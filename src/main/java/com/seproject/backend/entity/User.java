package com.seproject.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
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

    @OneToMany(mappedBy = "createdBy")
    private List<Project> createdProjects;
}
