package com.seproject.backend.controller;

import com.seproject.backend.dto.AssignUsersRequest;
import com.seproject.backend.dto.AssociateTeamspacesRequest;
import com.seproject.backend.dto.CreateTaskRequest;
import com.seproject.backend.dto.TaskResponse;
import com.seproject.backend.dto.UpdateTaskRequest;
import com.seproject.backend.dto.SuccessResponse;
import com.seproject.backend.entity.Task;
import com.seproject.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{teamspaceId}/task")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Integer teamspaceId,
            @Valid @RequestBody CreateTaskRequest req
    ) {
        Task created = taskService.createTask(teamspaceId, req);
        TaskResponse resp = toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId) {
        Task t = taskService.getTaskById(taskId);
        return ResponseEntity.ok(t);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Integer taskId,
            @Valid @RequestBody UpdateTaskRequest req
    ) {
        Task updated = taskService.updateTask(taskId, req);
        TaskResponse resp = toResponse(updated);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<SuccessResponse> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new SuccessResponse("Task deleted successfully"));
    }

    private TaskResponse toResponse(Task t) {
        TaskResponse r = new TaskResponse();
        r.setTaskId(t.getTaskId());
        r.setTeamspaceId(
            t.getTeamspaces() != null && !t.getTeamspaces().isEmpty()
                ? t.getTeamspaces().get(0).getId()
                : null
        );
        r.setName(t.getName());
        r.setCreationDate(t.getCreationDate());
        r.setDeadline(t.getDeadline());
        r.setStatus(t.getStatus());
        r.setDescription(t.getDescription());
        return r;
    }
    @PostMapping("/{taskId}/assignees")
    public ResponseEntity<SuccessResponse> assignUsers(
            @PathVariable Integer taskId,
            @Valid @RequestBody AssignUsersRequest req) {
        taskService.assignUsers(taskId, req.getUserIds());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new SuccessResponse("Users assigned to task"));
    }

    @PostMapping("/{taskId}/teamspaces")
    public ResponseEntity<SuccessResponse> associateTeamspaces(
        @PathVariable Integer taskId,
        @Valid @RequestBody AssociateTeamspacesRequest req) {
        taskService.associateTeamspaces(taskId, req.getTeamspaceIds());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new SuccessResponse("Teamspaces associated with task"));
    }
}











/*package com.seproject.backend.controller;

import com.seproject.backend.dto.CreateTaskRequest;
import com.seproject.backend.dto.TaskResponse;
import com.seproject.backend.dto.SuccessResponse;
import com.seproject.backend.entity.Task;
import com.seproject.backend.service.TaskService;
import com.seproject.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final JwtUtil jwtUtil;

    public TaskController(TaskService taskService, JwtUtil jwtUtil) {
        this.taskService = taskService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{teamspaceId}/task")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Integer teamspaceId,
            @Valid @RequestBody CreateTaskRequest req,
            HttpServletRequest request
    ) {
        // 1. Extract token
        String token = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(c -> "access_token".equals(c.getName()))
                        .findFirst())
                .map(Cookie::getValue)
                .orElseThrow(() -> new com.seproject.backend.exceptions.AccessDeniedException("No access token"));

        // 2. Decode
        Claims claims = jwtUtil.decodeJwtToken(token);
        Integer userId = claims.get("userId", Integer.class);
        String role    = claims.get("role", String.class);

        // 3. Delegate; let ResourceNotFoundException or AccessDeniedException bubble
        Task created = taskService.createTask(teamspaceId, req, userId, role);

        // 4. Map to DTO
        TaskResponse resp = new TaskResponse();
        resp.setTaskId(created.getTaskId());
        resp.setTeamspaceId(
            created.getTeamspaces() != null && !created.getTeamspaces().isEmpty()
                ? created.getTeamspaces().get(0).getId()
                : null
        );
        resp.setName(created.getName());
        resp.setCreationDate(created.getCreationDate());
        resp.setDeadline(created.getDeadline());
        resp.setStatus(created.getStatus());
        resp.setDescription(created.getDescription());

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Integer taskId,
            @RequestBody Task updates
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, updates));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<SuccessResponse> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new SuccessResponse("Task deleted successfully"));
    }
}
*/