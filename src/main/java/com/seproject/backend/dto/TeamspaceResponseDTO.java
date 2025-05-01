package com.seproject.backend.dto;

import java.time.LocalDateTime;

public class TeamspaceResponseDTO {
    private Integer teamspaceId;
    private Integer projectId;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private Integer creatorId;

    public TeamspaceResponseDTO() {}

    public TeamspaceResponseDTO(Integer teamspaceId, Integer projectId, String name, String description, LocalDateTime creationDate, Integer creatorId) {
        this.teamspaceId = teamspaceId;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public Integer getTeamspaceId() {
        return teamspaceId;
    }

    public void setTeamspaceId(Integer teamspaceId) {
        this.teamspaceId = teamspaceId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
