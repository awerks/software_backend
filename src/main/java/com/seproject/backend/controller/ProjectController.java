package com.seproject.backend.controller;

import com.seproject.backend.entity.Project;
import com.seproject.backend.service.ProjectService;
import com.seproject.backend.annotations.RoleRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProjectController
 * 
 * This controller handles HTTP requests related to project management.
 * It provides endpoints for creating, retrieving, updating, and deleting projects.
 * 
 * Key features:
 * - CRUD operations for projects
 * - Role-based access control
 * - Proper response handling
 * - Input validation
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * Creates a new project
     * @param project The project data
     * @param userId The ID of the creator (from authentication)
     * @return The created project
     */
    @PostMapping
    @RoleRequired("project_manager")
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("X-User-ID") Integer userId) {
        
        Project createdProject = projectService.createProject(project, userId);
        return ResponseEntity.ok(createdProject);
    }

    /**
     * Retrieves all projects
     * @return List of all projects
     */
    @GetMapping
    @RoleRequired("user")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    /**
     * Retrieves a specific project by ID
     * @param projectId The ID of the project
     * @return The project if found
     */
    @GetMapping("/{projectId}")
    @RoleRequired("user")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId) {
        
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    /**
     * Retrieves all projects created by a specific user
     * @param userId The ID of the user
     * @return List of projects created by the user
     */
    @GetMapping("/user/{userId}")
    @RoleRequired("user")
    public ResponseEntity<List<Project>> getProjectsByUserId(
            @PathVariable Long userId) {
        
        List<Project> projects = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Updates a project
     * @param projectId The ID of the project to update
     * @param project The updated project data
     * @return The updated project
     */
    @PutMapping("/{projectId}")
    @RoleRequired("project_manager")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project project) {
        
        Project updatedProject = projectService.updateProject(projectId, project);
        return ResponseEntity.ok(updatedProject);
    }

    /**
     * Deletes a project
     * @param projectId The ID of the project to delete
     * @return No content on success
     */
    @DeleteMapping("/{projectId}")
    @RoleRequired("project_manager")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long projectId) {
        
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
} 