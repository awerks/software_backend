package com.seproject.backend.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class VerifyToken {

    @NotEmpty(message = "Token is required")
    @Size(max = 255, message = "Token must be at most 255 characters long")
    private String token;
}
