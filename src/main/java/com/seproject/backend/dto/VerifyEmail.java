package com.seproject.backend.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyEmail {
    @NotEmpty(message = "Token is required")
    @Length(max = 255, message = "Token must be at most 255 characters long")
    private String token;

}
