package com.seproject.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seproject.backend.dto.TeamspaceDeleteRequest;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.repository.TeamspacesRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/teamspaces")
public class TeamspacesController {
    
    @Autowired
    private TeamspacesRepository teamspaceRepository;

    @Operation(summary = "Delete a teamspace", tags = {
        "Teamspaces" }, description = "Delete a teamspace  and returns a teamspace response upon the request.")
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teamspace successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teamspace.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden (insufficient privileges)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teamspace.class))),
        @ApiResponse(responseCode = "404", description = "Teamspace not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teamspace.class)))
    })

    @PostMapping("/{teamspaceId}")
    public ResponseEntity<?> deleteTeamspace(@PathVariable Integer teamspaceId,
            @RequestBody TeamspaceDeleteRequest teamspaceDeleteRequest, HttpServletRequest request) {

        if (!teamspaceRepository.findByTeamspaceId(teamspaceId).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Teamspace not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if (!teamspaceDeleteRequest.getCreatorId().equals(teamspaceDeleteRequest.getUserId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "You are not authorized to delete this teamspace");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        /*if (!teamspaceRepository.findByTeamspaceId(teamspaceId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).Map.of("Error:","Error: request was not successful");
        }
        if (!teamspaceDeleteRequest.getCreatorId().equals(teamspaceDeleteRequest.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error", "Error: request was not successful");
        }*/
        teamspaceRepository.deleteById(teamspaceId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Teamspace deleted successfully");
        return ResponseEntity.ok(response);
        //return ResponseEntity.status(HttpStatus.OK).body("message", "Teamspace deleted successfully");
    }
}