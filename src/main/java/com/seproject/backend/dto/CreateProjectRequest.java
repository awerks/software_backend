package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateProjectRequest {
    private String name;
    private String description;

    @JsonProperty("created_by")
    private Integer createdBy;
}
