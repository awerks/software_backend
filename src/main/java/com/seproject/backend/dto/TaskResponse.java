package com.seproject.backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskResponse {
  private Integer taskId;
  private Integer teamspaceId;
  private String name;
  private LocalDateTime creationDate;
  private LocalDateTime deadline;
  private String status;
  private String description;
}
