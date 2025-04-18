package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity representing a teamspace member in the database
 * Maps to the teamspace_members table
 */
@Entity
@Table(name = "teamspace_members")
@Data
public class TeamspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "teamspace_id", nullable = false)
    private Integer teamspaceId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "role")
    private String role;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
    }
} 