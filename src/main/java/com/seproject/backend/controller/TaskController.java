package com.seproject.backend.controller;

import com.seproject.backend.dto.AssignUsersRequest;
import com.seproject.backend.dto.AssociateTeamspacesRequest;
import com.seproject.backend.dto.CreateTaskRequest;
import com.seproject.backend.dto.TaskResponse;
import com.seproject.backend.dto.UpdateTaskRequest;
import com.seproject.backend.dto.SuccessResponse;
import com.seproject.backend.entity.Task;
import com.seproject.backend.exceptions.ResourceNotFoundException;
import com.seproject.backend.service.TaskService;
import com.seproject.backend.util.SessionUtil;
import com.seproject.backend.util.JwtUtil;
import com.seproject.backend.annotations.RoleRequired;
import com.seproject.backend.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService, JwtUtil jwtUtil) {
        this.taskService = taskService;
    }

    @PostMapping("/{teamspaceId}/task")
    @RoleRequired("teamlead")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Integer teamspaceId,
            @Valid @RequestBody CreateTaskRequest req,
            HttpServletRequest request) {
        Integer userId = SessionUtil.getUserId(request);

        Task created = taskService.createTask(teamspaceId, req, userId);
        TaskResponse resp = toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    @RoleRequired("teamlead")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    @RoleRequired("teamlead")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId) {
        Task t = taskService.getTaskById(taskId);
        return ResponseEntity.ok(t);
    }

    @PutMapping("/{taskId}")
    @RoleRequired("teamlead")
    public ResponseEntity<SuccessResponse> updateTask(
            @PathVariable Integer taskId,
            @Valid @RequestBody UpdateTaskRequest req) {
        taskService.updateTask(taskId, req);
        return ResponseEntity
                .ok(new SuccessResponse("The request was successful."));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Error: request was not successful"));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Error: request was not successful"));
    }

    @DeleteMapping("/{taskId}")
    @RoleRequired("teamlead")
    public ResponseEntity<SuccessResponse> deleteTask(
            @PathVariable Integer taskId,
            HttpServletRequest request) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new SuccessResponse("Task deleted successfully"));
    }

    @PostMapping("/{taskId}/assignees")
    @RoleRequired("teamlead")
    public ResponseEntity<SuccessResponse> assignUsers(
            @PathVariable Integer taskId,
            @Valid @RequestBody AssignUsersRequest req) {
        taskService.assignUsers(taskId, req.getUserIds());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Users assigned to task"));
    }

    @PostMapping("/{taskId}/teamspaces")
    @RoleRequired("teamlead")
    public ResponseEntity<SuccessResponse> associateTeamspaces(
            @PathVariable Integer taskId,
            @Valid @RequestBody AssociateTeamspacesRequest req) {
        taskService.associateTeamspaces(taskId, req.getTeamspaceIds());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Teamspaces associated with task"));
    }

    private TaskResponse toResponse(Task t) {
        TaskResponse r = new TaskResponse();
        r.setTaskId(t.getTaskId());
        r.setTeamspaceId(
                t.getTeamspaces() != null && !t.getTeamspaces().isEmpty()
                        ? t.getTeamspaces().get(0).getId()
                        : null);
        r.setName(t.getName());
        r.setCreationDate(t.getCreationDate());
        r.setDeadline(t.getDeadline());
        r.setStatus(t.getStatus());
        r.setDescription(t.getDescription());
        return r;
    }
}
