package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Teamspace Entity
 * 
 * This entity represents a teamspace in the system.
 * A teamspace is a collaborative environment where users can work together.
 * 
 * Key features:
 * - Unique teamspace ID
 * - Name and description
 * - Creation date
 * - Creator relationship
 * - Project relationship
 * - One-to-Many relationship with ChatMessages
 */
@Entity
@Table(name = "teamspaces")
@Data
public class Teamspace {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamspace_id")
    private Long teamspaceId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "teamspace", cascade = CascadeType.ALL)
    private List<ChatMessage> messages;
} 