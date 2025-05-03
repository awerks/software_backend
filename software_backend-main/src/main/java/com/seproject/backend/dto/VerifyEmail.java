package com.seproject.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyEmail {
    @NotEmpty(message = "Token is required")
    @Size(max = 255, message = "Token must be at most 255 characters long")
    private String token;

}
