package com.seproject.backend.service;

import com.seproject.backend.entity.Project;
import com.seproject.backend.exception.ResourceNotFoundException;

import java.util.List;

/**
 * ProjectService
 * 
 * This service interface defines the contract for project-related business operations.
 * It provides methods for managing projects and their associated resources.
 * 
 * Key features:
 * - CRUD operations for projects
 * - Project-specific business logic
 * - Error handling
 * - Data validation
 */
public interface ProjectService {
    
    /**
     * Creates a new project
     * @param project The project to create
     * @param creatorId The ID of the user creating the project
     * @return The created project
     */
    Project createProject(Project project, Integer creatorId);

    /**
     * Retrieves all projects
     * @return List of all projects
     */
    List<Project> getAllProjects();

    /**
     * Retrieves a specific project by ID
     * @param projectId The ID of the project
     * @return The project if found
     * @throws ResourceNotFoundException if project not found
     */
    Project getProjectById(Long projectId);

    /**
     * Retrieves all projects created by a specific user
     * @param userId The ID of the user
     * @return List of projects created by the user
     */
    List<Project> getProjectsByUserId(Long userId);

    /**
     * Updates a project
     * @param projectId The ID of the project to update
     * @param project The updated project data
     * @return The updated project
     * @throws ResourceNotFoundException if project not found
     */
    Project updateProject(Long projectId, Project project);

    /**
     * Deletes a project
     * @param projectId The ID of the project to delete
     * @throws ResourceNotFoundException if project not found
     */
    void deleteProject(Long projectId);
} 