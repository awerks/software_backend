package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerificationRequest {
    @JsonAlias({ "username_or_email" })
    @NotEmpty(message = "Username or Email is required")
    private String usernameOrEmail;
}
