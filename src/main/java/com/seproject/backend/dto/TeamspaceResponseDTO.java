package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamspaceResponseDTO {
    private Integer teamspaceId;
    private Integer projectId;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private Integer creatorId;
}
