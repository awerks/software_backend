package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProjectRequest {
    @NotEmpty(message = "Project name cannot be empty")
    private String name;
    @NotEmpty(message = "Project description cannot be empty")
    private String description;

    @JsonProperty("created_by")
    @NotNull(message = "Created by ID cannot be empty")
    private Integer createdBy;

}
