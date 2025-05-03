package com.seproject.backend.controller;

import com.seproject.backend.annotations.RoleRequired;
import com.seproject.backend.dto.*;
import com.seproject.backend.dto.task.*;
import com.seproject.backend.entity.Task;
import com.seproject.backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService svc;

    public TaskController(TaskService svc) { this.svc = svc; }

    // POST /api/tasks/{teamspaceId}/task
    @RoleRequired("TEAMSPACE_LEAD")
    @PostMapping("/{teamspaceId}/task")
    public ResponseEntity<TaskResponseDTO> create(
            @PathVariable Integer teamspaceId,
            @RequestBody TaskRequestDTO body) {

        Task t = svc.create(teamspaceId, body);
        return new ResponseEntity<>(toDto(t), HttpStatus.CREATED);
    }

    // POST /api/tasks/{taskId}/assignees
    @RoleRequired("TEAMSPACE_LEAD")
    @PostMapping("/{taskId}/assignees")
    public ResponseEntity<SuccessResponse> assign(
            @PathVariable Integer taskId,
            @RequestBody AssignUsersRequest body) {

        svc.assignUsers(taskId, body);
        return new ResponseEntity<>(
                new SuccessResponse("Task assigned to users"),
                HttpStatus.CREATED);
    }

    @RoleRequired("TEAMSPACE_LEAD")
    @PostMapping("/{taskId}/teamspaces")
    public ResponseEntity<SuccessResponse> link(
            @PathVariable Integer taskId,
            @RequestBody AssociateTeamspacesRequest body) {

        svc.linkTeamspaces(taskId, body);
        return new ResponseEntity<>(
                new SuccessResponse("Task associated with teamspace"),
                HttpStatus.CREATED);
    }

    @RoleRequired("TEAMSPACE_LEAD")
    @PutMapping("/{taskId}")
    public ResponseEntity<SuccessResponse> updateStatus(
            @PathVariable Integer taskId,
            @RequestBody UpdateTaskStatusRequest body) {

        svc.updateStatus(taskId, body);
        return ResponseEntity.ok(new SuccessResponse("Task status updated"));
    }

    private TaskResponseDTO toDto(Task t) {
        return new TaskResponseDTO(
                t.getTaskId(),
                t.getTeamspaceId(),
                t.getName(),
                t.getDescription(),
                t.getCreationDate().toString(),
                t.getDeadline().toString(),
                t.getStatus()
        );
    }
}
