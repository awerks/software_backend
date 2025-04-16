package com.seproject.backend.repository;

import com.seproject.backend.model.Teamspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Teamspace entity
 * 
 * Provides methods to perform CRUD operations and custom queries for Teamspace entities.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 */
@Repository
public interface TeamspaceRepository extends JpaRepository<Teamspace, Long> {
    
    /**
     * Find all teamspaces associated with a specific project
     * @param projectId The ID of the project
     * @return List of teamspaces belonging to the project
     */
    List<Teamspace> findByProjectId(Long projectId);

    /**
     * Find all teamspaces created by a specific user
     * @param creatorId The ID of the creator
     * @return List of teamspaces created by the user
     */
    List<Teamspace> findByCreatorId(Long creatorId);

    /**
     * Find a teamspace by its name within a specific project
     * @param name The name of the teamspace
     * @param projectId The ID of the project
     * @return Optional containing the teamspace if found
     */
    Optional<Teamspace> findByNameAndProjectId(String name, Long projectId);

    /**
     * Check if a teamspace exists in a project
     * @param name The name of the teamspace
     * @param projectId The ID of the project
     * @return true if the teamspace exists, false otherwise
     */
    boolean existsByNameAndProjectId(String name, Long projectId);
} 