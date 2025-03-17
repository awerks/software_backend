package com.seproject.backend.controller;

import com.seproject.backend.dto.AuthResponse;
import com.seproject.backend.dto.LoginRequest;
import com.seproject.backend.dto.ResetRequest;
import com.seproject.backend.dto.UserRegistration;
import com.seproject.backend.entity.Token;
import com.seproject.backend.entity.User;

import com.seproject.backend.repository.TokenRepository;
import com.seproject.backend.repository.UserRepository;
import com.seproject.backend.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.seproject.backend.service.EmailSender;

import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TokenRepository tokenRepository;


    @Operation(summary = "Login a user", tags = {
            "Authentication" }, description = "Authenticates a user by email and password, returning a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .filter(user -> user.getPassword().equals(loginRequest.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateJwtToken(user.getUsername(), user.getRole());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Invalid credentials")));
    }

    @Operation(summary = "Register a new user", tags = {
            "Authentication" }, description = "Creates a new user account and returns a user response upon successful registration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Username already exists", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistration registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setBirthdate(registerRequest.getBirthdate());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setRole(registerRequest.getRole());

        User savedUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary="Request a password reset link", tags = {"Authentication"}, description="Generates a password reset token and sends a reset email.")
    @ApiResponses(value={
            @ApiResponse(responseCode ="200", description="Reset link sent successfully.",content=@Content(mediaType="text/plain")),
            @ApiResponse(responseCode="400", description="User not found.", content=@Content(mediaType ="text/plain"))
    })

    @PostMapping("/request-reset")
    public ResponseEntity<?> requestreset(@RequestBody ResetRequest resetRequest) {
        String email;
        User user;

        if(resetRequest.getEmail() != null){
            if(userRepository.findByEmail(resetRequest.getEmail()).isPresent()) {
                user = userRepository.findByEmail(resetRequest.getEmail()).get();
                email = resetRequest.getEmail();
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
        }
        else {

            if (userRepository.findByUsername(resetRequest.getUsername()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
            }

            user = userRepository.findByUsername(resetRequest.getUsername()).get();
            email = user.getEmail();
        }

        String token= jwtUtil.generateResetToken(email);

        Token newToken = new Token();

        newToken.setToken(token);
        newToken.setUser(user);
        newToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        newToken.setUsed(false);

        tokenRepository.save(newToken);

        String subject="Password Reset Link";
        String body="Hello,\n\n" +
                "You requested to reset your password. Click the link below to proceed:\n" +
                "https://se-project.up.railway.app/reset-password?token="+token+"\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Sincerelly,\nDashpress";

        emailSender.sendEmail(email,subject,body);

        return ResponseEntity.ok("Reset link sent successfully.");
    }
}