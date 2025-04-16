package com.seproject.backend.service.impl;

import com.seproject.backend.model.Teamspace;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.service.TeamspaceService;
import com.seproject.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TeamspaceServiceImpl
 * 
 * This class extends the base TeamspaceService and provides the concrete implementation
 * of teamspace-related business logic. It handles all CRUD operations and additional
 * business rules for teamspaces.
 * 
 * Key responsibilities:
 * - CRUD operations for teamspaces
 * - Business logic validation
 * - Error handling
 * - Data transformation
 */
@Service
@Transactional
public class TeamspaceServiceImpl extends TeamspaceService {

    @Autowired
    private TeamspaceRepository teamspaceRepository;

    /**
     * Creates a new teamspace
     * @param teamspace The teamspace to create
     * @param creatorId The ID of the user creating the teamspace
     * @return The created teamspace
     */
    @Override
    public Teamspace createTeamspace(Teamspace teamspace, Long creatorId) {
        teamspace.setCreatorId(creatorId);
        return teamspaceRepository.save(teamspace);
    }

    /**
     * Retrieves all teamspaces for a project
     * @param projectId The ID of the project
     * @return List of teamspaces
     */
    @Override
    public List<Teamspace> getTeamspacesByProject(Long projectId) {
        return teamspaceRepository.findByProjectId(projectId);
    }

    /**
     * Retrieves a specific teamspace by ID
     * @param teamspaceId The ID of the teamspace
     * @return The teamspace if found
     * @throws ResourceNotFoundException if teamspace not found
     */
    @Override
    public Teamspace getTeamspace(Long teamspaceId) {
        return teamspaceRepository.findById(teamspaceId)
            .orElseThrow(() -> new ResourceNotFoundException("Teamspace not found with id: " + teamspaceId));
    }

    /**
     * Updates a teamspace
     * @param teamspace The teamspace with updated data
     * @return The updated teamspace
     * @throws ResourceNotFoundException if teamspace not found
     */
    @Override
    public Teamspace updateTeamspace(Teamspace teamspace) {
        // Verify existence
        getTeamspace(teamspace.getId());
        return teamspaceRepository.save(teamspace);
    }

    /**
     * Deletes a teamspace
     * @param teamspaceId The ID of the teamspace to delete
     * @throws ResourceNotFoundException if teamspace not found
     */
    @Override
    public void deleteTeamspace(Long teamspaceId) {
        // Verify existence
        getTeamspace(teamspaceId);
        teamspaceRepository.deleteById(teamspaceId);
    }

    /**
     * Retrieves all teamspaces created by a specific user
     * @param creatorId The ID of the creator
     * @return List of teamspaces created by the user
     */
    public List<Teamspace> getTeamspacesByCreatorId(Long creatorId) {
        return teamspaceRepository.findByCreatorId(creatorId);
    }
} 