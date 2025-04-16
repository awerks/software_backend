package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.seproject.backend.model.Teamspace;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Project Entity
 * 
 * This entity represents a project in the system.
 * Projects can contain multiple teamspaces and are created by users.
 * 
 * Key features:
 * - Unique project ID
 * - Name and description
 * - Creation date
 * - Creator relationship
 * - One-to-Many relationship with Teamspaces
 */
@Entity
@Table(name = "projects")
@Data
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Teamspace> teamspaces;
} 