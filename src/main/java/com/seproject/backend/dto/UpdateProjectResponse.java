package com.seproject.backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdateProjectResponse {
    @JsonProperty("project_id")
    private Integer projectId;
    private String name;
    private String description;
    @JsonProperty("creation_date")
    private LocalDateTime creationDate;
}
