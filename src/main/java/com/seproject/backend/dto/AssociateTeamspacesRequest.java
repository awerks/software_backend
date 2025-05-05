package com.seproject.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class AssociateTeamspacesRequest {
    @NotEmpty(message = "teamspaceIds must not be empty")
    private List<Integer> teamspaceIds;

    public List<Integer> getTeamspaceIds() { return teamspaceIds; }
    public void setTeamspaceIds(List<Integer> teamspaceIds) { this.teamspaceIds = teamspaceIds; }
}