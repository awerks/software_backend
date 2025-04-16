package com.seproject.backend.controller;

import com.seproject.backend.model.Teamspace;
import com.seproject.backend.service.TeamspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TeamspaceController
 * 
 * This controller handles HTTP requests related to teamspaces.
 * It provides endpoints for creating, retrieving, updating, and deleting teamspaces.
 * 
 * Key features:
 * - CRUD operations for teamspaces
 * - Project-based teamspace management
 * - Proper response handling
 * - Input validation
 */
@RestController
@RequestMapping("/api/teamspaces")
public class TeamspaceController {

    @Autowired
    private TeamspaceService teamspaceService;

    /**
     * Creates a new teamspace
     * @param teamspace The teamspace data
     * @param userId The ID of the creator (from authentication)
     * @return The created teamspace
     */
    @PostMapping
    public ResponseEntity<Teamspace> createTeamspace(
            @RequestBody Teamspace teamspace,
            @RequestHeader("X-User-ID") Long userId) {
        
        Teamspace createdTeamspace = teamspaceService.createTeamspace(teamspace, userId);
        return ResponseEntity.ok(createdTeamspace);
    }

    /**
     * Retrieves all teamspaces for a project
     * @param projectId The ID of the project
     * @return List of teamspaces
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Teamspace>> getTeamspacesByProject(
            @PathVariable Long projectId) {
        
        List<Teamspace> teamspaces = teamspaceService.getTeamspacesByProject(projectId);
        return ResponseEntity.ok(teamspaces);
    }

    /**
     * Retrieves a specific teamspace by ID
     * @param teamspaceId The ID of the teamspace
     * @return The teamspace if found
     */
    @GetMapping("/{teamspaceId}")
    public ResponseEntity<Teamspace> getTeamspace(
            @PathVariable Long teamspaceId) {
        
        Teamspace teamspace = teamspaceService.getTeamspace(teamspaceId);
        return ResponseEntity.ok(teamspace);
    }

    /**
     * Updates a teamspace
     * @param teamspaceId The ID of the teamspace to update
     * @param teamspace The updated teamspace data
     * @return The updated teamspace
     */
    @PutMapping("/{teamspaceId}")
    public ResponseEntity<Teamspace> updateTeamspace(
            @PathVariable Long teamspaceId,
            @RequestBody Teamspace teamspace) {
        
        teamspace.setId(teamspaceId);
        Teamspace updatedTeamspace = teamspaceService.updateTeamspace(teamspace);
        return ResponseEntity.ok(updatedTeamspace);
    }

    /**
     * Deletes a teamspace
     * @param teamspaceId The ID of the teamspace to delete
     * @return No content on success
     */
    @DeleteMapping("/{teamspaceId}")
    public ResponseEntity<Void> deleteTeamspace(
            @PathVariable Long teamspaceId) {
        
        teamspaceService.deleteTeamspace(teamspaceId);
        return ResponseEntity.noContent().build();
    }
} 