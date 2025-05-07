package com.seproject.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateTaskRequest {

  @NotBlank(message = "Task name must not be blank")
  private String name;

  @NotNull(message = "Deadline must be provided")
  @Future(message = "Deadline must be in the future")
  private LocalDateTime deadline;

  private String description;
}
