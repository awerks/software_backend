package com.seproject.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

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
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "user";

    @Column(nullable = false)
    private boolean verified = false;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<ChatMessage> messages;
}
