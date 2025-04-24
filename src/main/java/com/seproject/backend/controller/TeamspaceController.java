package com.seproject.backend.controller;

import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.service.TeamspaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TeamspaceController {

    private final TeamspaceService teamspaceService;

    public TeamspaceController(TeamspaceService teamspaceService) {
        this.teamspaceService = teamspaceService;
    }

    @PostMapping("/projects/{projectId}/teamspaces")
    public ResponseEntity<?> createTeamspace(
            @PathVariable Integer projectId,
            @RequestBody Teamspace teamspace
    ) {
        try {
            Teamspace created = teamspaceService.createTeamspace(projectId, teamspace);

            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("error", "Error: request was not successful"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/teamspaces/{teamspaceId}")
    public ResponseEntity<?> deleteTeamspace(@PathVariable int teamspaceId) {
        if (teamspaceService.getTeamspaceById(teamspaceId).isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "Teamspace not found"), HttpStatus.NOT_FOUND);
        }

        teamspaceService.deleteTeamspace(teamspaceId);
        return ResponseEntity.ok(Map.of("message", "The request was successful."));
    }

    //TODO: implement add and delete users from teamspace
}
