package com.seproject.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetRequest {

    @NotEmpty(message = "Username or Email is required")
    private String usernameOrEmail;
}
