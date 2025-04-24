package com.seproject.backend.controller;

import com.seproject.backend.entity.Task;
import com.seproject.backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{teamspaceId}/task")
    public ResponseEntity<Task> addTask(@PathVariable("teamspaceId") Integer teamspaceId, @RequestBody Task task) {
        return new ResponseEntity<>(
                taskService.createTask(teamspaceId, task),
                HttpStatus.OK
        );
    }
}
