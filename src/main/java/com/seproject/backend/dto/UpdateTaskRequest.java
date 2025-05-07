package com.seproject.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @NotBlank(message = "Status must not be blank")
    private String status;
}