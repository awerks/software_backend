package com.seproject.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateProjectRequest {
    @NotEmpty(message = "Project name cannot be empty")
    private String name;
    @NotEmpty(message = "Project description cannot be empty")
    private String description;
}
