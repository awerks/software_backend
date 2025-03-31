package com.seproject.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "teamspaces")
public class Teamspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamspace_id")
    private Integer teamspaceId;

    @Column(nullable = false, length = 100)
    private String name;

    // TODO: check how to defıne default value
    @Column
    @Temporal(TemporalType.TIMESTAMP) // TODO: check the type
    private LocalDateTime creationDate;

    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column
    private String description;

    public Integer getTeamspaceId() {
        return teamspaceId;
    }

    public void setTeamspaceId(Integer teamspaceId) {
        this.teamspaceId = teamspaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
