package com.seproject.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponseDTO {
    private Integer projectId;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private Integer createdBy;
    private List<TeamspaceResponseDTO> teamspaces;

    public ProjectResponseDTO() {}

    public ProjectResponseDTO(Integer projectId, String name, String description, LocalDateTime creationDate, Integer createdBy, List<TeamspaceResponseDTO> teamspaces) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.teamspaces = teamspaces;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public List<TeamspaceResponseDTO> getTeamspaces() {
        return teamspaces;
    }

    public void setTeamspaces(List<TeamspaceResponseDTO> teamspaces) {
        this.teamspaces = teamspaces;
    }
}
