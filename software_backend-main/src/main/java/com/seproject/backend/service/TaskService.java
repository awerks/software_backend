package com.seproject.backend.service;

import com.seproject.backend.dto.task.AssignUsersRequest;
import com.seproject.backend.dto.task.AssociateTeamspacesRequest;
import com.seproject.backend.dto.task.TaskRequestDTO;
import com.seproject.backend.dto.task.UpdateTaskStatusRequest;
import com.seproject.backend.entity.Task;

public interface TaskService {
    Task create(Integer teamspaceId, TaskRequestDTO dto);
    void assignUsers(Integer taskId, AssignUsersRequest dto);
    void linkTeamspaces(Integer taskId, AssociateTeamspacesRequest dto);
    void updateStatus(Integer taskId, UpdateTaskStatusRequest dto);
}
