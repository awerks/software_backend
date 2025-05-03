package com.seproject.backend.service;

import com.seproject.backend.dto.task.AssignUsersRequest;
import com.seproject.backend.dto.task.AssociateTeamspacesRequest;
import com.seproject.backend.dto.task.TaskRequestDTO;
import com.seproject.backend.dto.task.UpdateTaskStatusRequest;
import com.seproject.backend.entity.Task;
import com.seproject.backend.exceptions.NotFoundException;
import com.seproject.backend.repository.TaskRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;

    public TaskServiceImpl(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public Task create(Integer teamspaceId, TaskRequestDTO dto) {
        Task t = new Task();
        t.setTeamspaceId(teamspaceId);
        t.setName(dto.getName());
        t.setDescription(dto.getDescription());
        t.setDeadline(LocalDateTime.parse(dto.getDeadline()));
        return repo.save(t);
    }

    @Override
    public void assignUsers(Integer taskId, AssignUsersRequest dto) {
        ensureTask(taskId);
        // LATER: Integrate with User service / notifications
    }
    

    @Override
    public void linkTeamspaces(Integer taskId, AssociateTeamspacesRequest dto) {
        ensureTask(taskId);
        // LATER: Persist linkage in a join table if we need it 
    }

    @Override
    public void updateStatus(Integer taskId, UpdateTaskStatusRequest dto) {
        Task t = ensureTask(taskId);
        t.setStatus(String.valueOf(dto.getStatus().toUpperCase()));
    }

    private Task ensureTask(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new NotFoundException("Task not found"));
    }
}
