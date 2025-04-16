package com.seproject.backend.repository;

import com.seproject.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProjectRepository
 * 
 * This repository interface provides data access methods for Project entities.
 * It extends JpaRepository to inherit basic CRUD operations and adds custom query methods
 * specific to project management.
 * 
 * Key features:
 * - Basic CRUD operations inherited from JpaRepository
 * - Custom queries for project-specific operations
 * - Proper transaction management
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find all projects created by a specific user
     * @param createdBy The ID of the user who created the projects
     * @return List of projects created by the user
     */
    List<Project> findByCreatedBy_UserId(Long createdBy);

    /**
     * Find a project by its name
     * @param name The name of the project
     * @return Optional containing the project if found
     */
    Optional<Project> findByName(String name);

    /**
     * Check if a project exists with the given name
     * @param name The name of the project
     * @return true if the project exists, false otherwise
     */
    boolean existsByName(String name);
} 