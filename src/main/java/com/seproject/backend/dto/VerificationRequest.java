package com.seproject.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class VerificationRequest {
    @JsonAlias({ "username_or_email" })
    private String usernameOrEmail;
}
