package com.seproject.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity
 * 
 * This entity represents a user in the system.
 * Users can be members of teamspaces and can send chat messages.
 * 
 * Key features:
 * - Unique user ID
 * - Basic user information (name, email, etc.)
 * - Authentication details
 * - Role-based access control
 * - One-to-Many relationship with ChatMessages
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    // ISO-8601
    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "verified", nullable = false)
    private boolean verified;
}
