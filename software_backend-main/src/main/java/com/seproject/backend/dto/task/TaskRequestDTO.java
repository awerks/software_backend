package com.seproject.backend.dto.task;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String name;
    private String description;
    private String deadline;
}
