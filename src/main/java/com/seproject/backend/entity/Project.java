package com.seproject.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
   
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }
}
