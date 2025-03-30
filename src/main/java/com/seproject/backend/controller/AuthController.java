package com.seproject.backend.controller;

import com.seproject.backend.dto.*;
import com.seproject.backend.entity.Token;
import com.seproject.backend.entity.User;

import com.seproject.backend.repository.TokenRepository;
import com.seproject.backend.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.seproject.backend.service.EmailSender;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.seproject.backend.util.JwtUtil;
import com.seproject.backend.util.SessionUtil;

import com.seproject.backend.annotations.RoleRequired;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${cookie.domain}")
    private String cookieDomain;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailSender emailSender;

    @Operation(summary = "Login a user", tags = {
            "Authentication" }, description = "Authenticates a user by username and password, returning a JWT token in an HTTP-only cookie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPayload.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail());
        if (userOptional.isEmpty()) {
            System.out.println("User not found: " + loginRequest.getUsernameOrEmail());
        }
        return userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail())
                .filter(user -> loginRequest.getPassword().equals(user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateJwtToken(user.getUsername(), user.getRole());
                    Cookie jwtTokenCookie = new Cookie("access_token", token);
                    jwtTokenCookie.setHttpOnly(true);
                    jwtTokenCookie.setPath("/");
                    jwtTokenCookie.setMaxAge(86400);
                    if (!cookieDomain.isEmpty()) {
                        jwtTokenCookie.setDomain(cookieDomain);
                    }
                    jwtTokenCookie.setSecure(true);
                    response.addCookie(jwtTokenCookie);

                    UserPayload userPayload = new UserPayload(
                            user.getUserId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getBirthdate(),
                            user.getUsername(),
                            user.getRole(),
                            new SuccessResponse("Login successful"));
                    return ResponseEntity.ok((Object) userPayload);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid credentials")));
    }

    @Operation(summary = "Logout a user", tags = {
            "Authentication" }, description = "Logs out the user by invalidating the JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtTokenCookie = new Cookie("access_token", null);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setMaxAge(0);
        jwtTokenCookie.setSecure(true);
        response.addCookie(jwtTokenCookie);

        return ResponseEntity.ok(new SuccessResponse("Logout successful"));
    }

    @Operation(summary = "Register a new user", tags = {
            "Authentication" }, description = "Creates a new user account and returns the a message upon successful registration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Username already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistration registerRequest) {
        if (userRepository.findByUsernameOrEmail(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Username already exists"));
        }

        User newUser = new User();
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setBirthdate(registerRequest.getBirthdate());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setRole(registerRequest.getRole());
        newUser.setVerified(false);

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("User registered successfully"));
    }

    @Operation(summary = "Request a password reset link", tags = {
            "Authentication" }, description = "Generates a password reset token and sends a reset email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset link sent successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/request-reset")
    public ResponseEntity<?> requestreset(@Valid @RequestBody ResetRequest resetRequest) {

        if (userRepository.findByUsernameOrEmail(resetRequest.getUsernameOrEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User not found."));
        }

        User user = userRepository.findByUsernameOrEmail(resetRequest.getUsernameOrEmail()).get();
        String email = user.getEmail();

        String token = jwtUtil.generateResetVerificationToken(email);

        Token newToken = new Token();

        newToken.setToken(token);
        newToken.setUser(user);
        newToken.setExpiresAt(LocalDateTime.now().plusMinutes(60));

        tokenRepository.save(newToken);

        emailSender.sendResetPasswordEmail(email, token);

        return ResponseEntity.ok(new SuccessResponse("Reset link sent successfully."));
    }

    @Operation(summary = "Verify if a password reset token is valid", tags = {
            "Authentication" }, description = "Checks if the token exists and is not expired.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid."),
            @ApiResponse(responseCode = "400", description = "Invalid token.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token expired or used", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@Valid @RequestBody VerifyToken verifyToken) {
        try {
            validateToken(verifyToken.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Token is valid"));
        } catch (Exception e) {
            HttpStatus status = "Invalid token".equals(e.getMessage()) ? HttpStatus.BAD_REQUEST : HttpStatus.FORBIDDEN;
            return ResponseEntity.status(status).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Reset the user's password", tags = {
            "Authentication" }, description = "Updates the password for the user associated with the provided token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid token or weak password. (Password must be at least 8 characters)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Token expired or used", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        try {
            Token token = validateToken(resetPassword.getToken());
            User user = token.getUser();
            user.setPassword(resetPassword.getPassword());
            userRepository.save(user);
            tokenRepository.delete(token);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Password reset successfully"));
        } catch (Exception e) {
            HttpStatus status = "Invalid token".equals(e.getMessage()) ? HttpStatus.BAD_REQUEST : HttpStatus.FORBIDDEN;
            return ResponseEntity.status(status).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Get a protected resource", tags = {
            "Authentication" }, description = "An example of a protected resource that requires minimum role. For example, only users with the 'user' role and higher can access this endpoint. (role hierarchy, admin > project_manager > teamlead > user)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access granted to protected resource.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized: user not logged in.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied: insufficient permissions.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/protected")
    @RoleRequired("user")
    public ResponseEntity<?> getProtectedResource(@NonNull HttpServletRequest request) {

        String username = SessionUtil.getUsername(request);
        String role = SessionUtil.getRole(request);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);

        return ResponseEntity.ok(new SuccessResponse("Access granted to protected resource."));
    }

    private Token validateToken(String tokenStr) throws Exception {
        Optional<Token> tokenOptional = tokenRepository.findByToken(tokenStr);
        if (tokenOptional.isEmpty()) {
            throw new Exception("Invalid token");
        }
        Token token = tokenOptional.get();
        if (token.isExpired() || token.isUsed()) {
            throw new Exception("Token expired or used");
        }
        return token;
    }

    @Operation(summary = "Request email verification link", tags = {
            "Authentication" }, description = "Sends an email with verification link to user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email sent successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Sending failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/send-verification-link")
    public ResponseEntity<?> requestVerificationLink(@Valid @RequestBody VerificationRequest verificationRequest) {

        String email = verificationRequest.getUsernameOrEmail();
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User not found."));
        }
        User user = userOptional.get();
        if (user.isVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already verified");
        }

        String token = jwtUtil.generateResetVerificationToken(email);

        Token newToken = new Token();

        newToken.setToken(token);
        newToken.setUser(user);
        newToken.setExpiresAt(LocalDateTime.now().plusMinutes(60));

        tokenRepository.save(newToken);

        emailSender.sendVerificationEmail(email, token);

        return ResponseEntity.ok(new SuccessResponse("Email verification link sent successfully."));
    }

    @Operation(summary = "Verify user's email", tags = {
            "Authentication" }, description = "Verifies user's email adress.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User verified successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Token not found, expired or used", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmail email) {

        Optional<Token> tokenOptional = tokenRepository.findByToken(email.getToken());
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid token"));
        }
        Token token = tokenOptional.get();
        if (token.isExpired() || token.isUsed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Token expired or used"));
        }

        User user = token.getUser();

        user.setVerified(true);
        userRepository.save(user);

        tokenRepository.delete(token);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("User verified successfully."));
    }
}