package com.seproject.backend.dto;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.seproject.backend.entity.Teamspace;

import lombok.Data;

@Data
public class TeamspaceCreationResponse {
    private Integer teamspaceId;
    private Integer projectId;
    private String name;
    private String description;
    private String creationDate;
    private Integer creatorId;

    public TeamspaceCreationResponse(Teamspace teamspace) {
        this.teamspaceId = teamspace.getTeamspaceId();
        this.projectId = teamspace.getProject().getProjectId();
        this.name = teamspace.getName();
        this.description = teamspace.getDescription();
        this.creationDate = teamspace.getCreationDate()
                .atZone(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
        this.creatorId = teamspace.getCreator().getUserId();
    }
}
