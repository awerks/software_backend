package com.seproject.backend.dto;

import java.time.LocalDateTime;

public class TeamspaceCreationResponse {
    private Integer teamspaceId;
    private Integer projectId;
    private String name;
    private LocalDateTime creationDate;
    private Integer creatorId;

    public Integer getTeamspaceId() {
        return teamspaceId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setTeamspaceId(Integer teamspaceId) {
        this.teamspaceId = teamspaceId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}
