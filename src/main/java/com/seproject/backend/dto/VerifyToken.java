package com.seproject.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class VerifyToken {

    @NotEmpty(message = "Token is required")
    private String token;
}
