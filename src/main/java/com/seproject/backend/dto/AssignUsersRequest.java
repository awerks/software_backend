package com.seproject.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class AssignUsersRequest {
    @NotEmpty(message = "userIds must not be empty")
    private List<Integer> userIds;

    public List<Integer> getUserIds() { return userIds; }
    public void setUserIds(List<Integer> userIds) { this.userIds = userIds; }
}