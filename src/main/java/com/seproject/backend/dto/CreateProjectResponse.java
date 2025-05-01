package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class CreateProjectResponse {
    private Integer projectId;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    @JsonProperty("created_by")
    private Integer createdBy;
    private List<TeamspaceResponseDTO> teamspaces;
}
