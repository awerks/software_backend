package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDateTime createdAt = LocalDateTime.now();

    //TODO: add usedId fetching from session or pass in the request
    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "project")
    private List<Teamspace> teamspaceList;
}
