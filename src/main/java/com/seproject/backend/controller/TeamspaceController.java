package com.seproject.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seproject.backend.dto.ErrorResponse;
import com.seproject.backend.dto.SuccessResponse;
import com.seproject.backend.dto.TeamspaceCreationRequest;
import com.seproject.backend.dto.TeamspaceCreationResponse;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.service.TeamspaceService;
import com.seproject.backend.util.SessionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TeamspaceController {

        private final TeamspaceService teamspaceService;

        public TeamspaceController(TeamspaceService teamspaceService) {
                this.teamspaceService = teamspaceService;
        }

        @Operation(summary = "Create a new teamspace under a specific project", tags = {
                        "Teamspaces" }, description = "Create a new teamspace under a specific project and returns a teamspace response upon successful creation.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Teamspace created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeamspaceCreationResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping("/projects/{projectId}/teamspaces")
        public ResponseEntity<?> createTeamspace(@PathVariable Integer projectId,
                        @RequestBody TeamspaceCreationRequest teamspaceCreationRequest,
                        @NonNull HttpServletRequest request) {
                Integer userId = SessionUtil.getUserId(request);
                Teamspace created = teamspaceService.createTeamspace(projectId, userId, teamspaceCreationRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(new TeamspaceCreationResponse(created));
        }

        @Operation(summary = "Delete a teamspace", tags = {
                        "Teamspaces" }, description = "Delete a teamspace and returns a teamspace response upon the request.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Teamspace successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
                        @ApiResponse(responseCode = "403", description = "Forbidden (insufficient privileges)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Teamspace not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        })
        @DeleteMapping("/teamspaces/{teamspaceId}")
        public ResponseEntity<?> deleteTeamspace(@PathVariable Integer teamspaceId, @NonNull HttpServletRequest request) {
                Integer userId = SessionUtil.getUserId(request);
                teamspaceService.deleteTeamspace(teamspaceId, userId);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Teamspace deleted successfully"));
        }
}
