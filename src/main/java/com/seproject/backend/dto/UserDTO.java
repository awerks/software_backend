package com.seproject.backend.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * UserDTO (Data Transfer Object)
 * 
 * This DTO is used to transfer user data between the client and server.
 * It contains only the necessary fields for user information display.
 * 
 * Key features:
 * - User ID
 * - Basic user information
 * - No sensitive data (password)
 */
@Data
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private LocalDate birthdate;
    private String role;
    private boolean verified;
} 