package com.seproject.backend.dto.task;

import lombok.Data;

import java.util.List;

@Data
public class AssociateTeamspacesRequest {
    private List<Integer> teamspaceIds;
}
