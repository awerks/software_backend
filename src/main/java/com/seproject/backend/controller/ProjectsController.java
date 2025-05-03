package com.seproject.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.seproject.backend.dto.TeamspaceCreationRequest;
import com.seproject.backend.dto.TeamspaceCreationResponse;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.ProjectRepository;
import com.seproject.backend.repository.TeamspacesRepository;
import com.seproject.backend.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamspacesRepository teamspaceRepository;

    @Autowired
    private TeamspacesRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Create a new teamspace under a specific project", tags = {
            "Teamspaces" }, description = "Create a new teamspace under a specific project and returns a teamspace response upon successful creation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teamspace created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Teamspace.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/plain"))// it
                                                                                                                         // says
                                                                                                                         // json
    })

    @PostMapping("/{projectId}/teamspaces")
    public ResponseEntity<?> createTeamspace(@PathVariable Integer projectId,
            @RequestBody TeamspaceCreationRequest teamspaceCreationRequest, HttpServletRequest request) {

        if (!projectRepository.findByProjectId(projectId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This project does not exists");// should ı change
                                                                                                      // the text
        }
        if (teamspaceRepository.findByName(teamspaceCreationRequest.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This teamspace name is already been used");
        }

        Teamspace newTeamspace = new Teamspace();
        newTeamspace.setName(teamspaceCreationRequest.getName());
        newTeamspace.setProjectId(projectId);
        newTeamspace.setDescription(teamspaceCreationRequest.getDescription());

        // Integer creatorId = (Integer)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // jwtUtil.decodeJwtToken(token).get("creatorId", Integer.class);
        // TODO: get creatorId
        newTeamspace.setCreatorId(creatorId);

        Teamspace savedTeamspace = teamspaceRepository.save(newTeamspace);

        TeamspaceCreationResponse response = new TeamspaceCreationResponse();
        response.setTeamspaceId(savedTeamspace.getTeamspaceId());
        response.setProjectId(savedTeamspace.getProjectId());
        response.setName(savedTeamspace.getName());
        response.setCreationDate(savedTeamspace.getCreationDate());
        response.setCreatorId(savedTeamspace.getCreatorId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
