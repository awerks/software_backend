package com.seproject.backend.dto;

public class TeamspaceDeleteRequest {
    
    private Integer teamspaceId;
    private Integer creatorId;
    private Integer userId;

    public void setTeamspaceId(Integer teamspaceId) {
        this.teamspaceId = teamspaceId;
    }
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamspaceId() {
        return teamspaceId;
    }
    public Integer getCreatorId() {
        return creatorId;
    }
    public Integer getUserId() {
        return userId;
    }
}
