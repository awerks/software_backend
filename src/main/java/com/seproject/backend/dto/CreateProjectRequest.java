package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProjectRequest {
    private String name;
    private String description;

    @JsonProperty("created_by")
    private Integer createdBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
