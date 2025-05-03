package com.seproject.backend.dto.task;

import lombok.Data;

import java.util.List;

@Data
public class AssignUsersRequest {
    private List<Long> userIds;
}
