package com.seproject.backend.service;

import com.seproject.backend.model.Teamspace;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TeamspaceService
 * 
 * This service handles the business logic for teamspace operations.
 * It acts as an intermediary between the controller and repository layers,
 * providing additional validation, error handling, and business rules.
 * 
 * Key responsibilities:
 * - CRUD operations for teamspaces
 * - Business logic validation
 * - Error handling
 * - Data transformation if needed
 */
@Service
public class TeamspaceService {

    @Autowired
    private TeamspaceRepository teamspaceRepository;

    /**
     * Creates a new teamspace
     * @param teamspace The teamspace to create
     * @param creatorId The ID of the user creating the teamspace
     * @return The created teamspace
     */
    public Teamspace createTeamspace(Teamspace teamspace, Long creatorId) {
        teamspace.setCreatorId(creatorId);
        return teamspaceRepository.save(teamspace);
    }

    /**
     * Retrieves all teamspaces for a project
     * @param projectId The ID of the project
     * @return List of teamspaces
     */
    public List<Teamspace> getTeamspacesByProject(Long projectId) {
        return teamspaceRepository.findByProjectId(projectId);
    }

    /**
     * Retrieves a specific teamspace by ID
     * @param teamspaceId The ID of the teamspace
     * @return The teamspace if found
     * @throws ResourceNotFoundException if teamspace not found
     */
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
    public void deleteTeamspace(Long teamspaceId) {
        // Verify existence
        getTeamspace(teamspaceId);
        teamspaceRepository.deleteById(teamspaceId);
    }
} 