package com.seproject.backend.repository;

import com.seproject.backend.entity.Teamspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * TeamspaceRepository
 * 
 * This repository handles all database operations related to teamspaces.
 * It extends JpaRepository to provide basic CRUD operations and custom query methods.
 * 
 * Key features:
 * - Basic CRUD operations inherited from JpaRepository
 * - Custom query methods for teamspace retrieval
 * - Project-based teamspace queries
 */
@Repository
public interface TeamspaceRepository extends JpaRepository<Teamspace, Long> {
    
    /**
     * Retrieves all teamspaces for a specific project
     * @param projectId The ID of the project
     * @return List of teamspaces
     */
    List<Teamspace> findByProject_ProjectId(Long projectId);
    
    /**
     * Retrieves all teamspaces created by a specific user
     * @param creatorId The ID of the creator
     * @return List of teamspaces
     */
    List<Teamspace> findByCreator_UserId(Long creatorId);
} 