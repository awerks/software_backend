package com.seproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddUserToTeamspaceResponse {
    private Integer teamspaceId;
    private Integer userId;
    private String role;
}