package com.seproject.backend.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private Integer taskId;
    private Integer teamspaceId;
    private String name;
    private String description;
    private String creationDate;
    private String deadline;
    private String status;
}
