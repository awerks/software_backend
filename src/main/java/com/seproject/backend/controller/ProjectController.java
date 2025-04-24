package com.seproject.backend.controller;

import com.seproject.backend.entity.Project;
import com.seproject.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return new ResponseEntity<>(
                projectService.save(project),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(
                projectService.getAllProjects(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Integer id) {
        Optional<Project> project = projectService.getProjectById(id);

        return project.map(value -> new ResponseEntity<>(
                value,
                HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        ));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Project> updateProjectById(@RequestBody Project project, @PathVariable("id") Integer projectId) {  // Changed type to Long
        project.setProjectId(projectId);

        return new ResponseEntity<>(
                projectService.save(project),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProjectById(@PathVariable("id") Integer projectId) {  // Changed type to Long
        projectService.deleteProjectById(projectId);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
