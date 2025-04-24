package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="projects")
public class Project {

    @Id
    @Column(name="project_id")
    private int projectId;

    @Column(name="name")
    private String name;

    @Column(name="creation_date")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(referencedColumnName = "project_id", name = "created_by")
    private User createdBy;
}
