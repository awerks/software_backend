package com.seproject.backend.dto;

import lombok.Data;

@Data

public class AddUserToTeamspaceRequest {
   
    private Integer userId;
    private String role;

    public AddUserToTeamspaceRequest() {}
    public AddUserToTeamspaceRequest(Integer userId, String role) {
        this.userId = userId;
        this.role = role;
    }

}

