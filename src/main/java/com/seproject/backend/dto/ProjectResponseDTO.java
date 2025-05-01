package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {
    private Integer projectId;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private Integer createdBy;
    private List<TeamspaceResponseDTO> teamspaces;
}
