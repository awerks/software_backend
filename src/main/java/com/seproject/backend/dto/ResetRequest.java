package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetRequest {

    @NotEmpty(message = "Username or Email is required")
    @JsonAlias({ "username_or_email" })
    private String usernameOrEmail;
}
