package com.seproject.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UserRegistration {

    @NotEmpty(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Past(message = "Birthdate must be in the past")
    private LocalDateTime birthdate;

    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^\\S+$", message = "Username must not contain spaces")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Role is required")
    @Pattern(regexp = "^(admin|user|project_manager|teamlead)$", message = "Role must be either 'admin' or 'project_manager' or 'teamlead or 'user' ")
    private String role;
}
