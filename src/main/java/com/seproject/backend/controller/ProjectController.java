package com.seproject.backend.controller;

import com.seproject.backend.dto.CreateProjectRequest;
import com.seproject.backend.dto.ProjectResponseDTO;
import com.seproject.backend.dto.TeamspaceResponseDTO;
import com.seproject.backend.entity.Project;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.UserRepository;
import com.seproject.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody CreateProjectRequest request) {
        if (request.getCreatedBy() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepository.findById(request.getCreatedBy());
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCreatedBy(userOpt.get());

        Project saved = projectService.save(project);

        ProjectResponseDTO response = new ProjectResponseDTO(
                saved.getProjectId(),
                saved.getName(),
                saved.getDescription(),
                saved.getCreatedAt(),
                project.getCreatedBy() != null ? project.getCreatedBy().getUserId() : null,
                List.of() // No teamspaces created yet
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectResponseDTO> dtos = projects.stream().map(p -> new ProjectResponseDTO(
                p.getProjectId(),
                p.getName(),
                p.getDescription(),
                p.getCreatedAt(),
                p.getCreatedBy() != null ? p.getCreatedBy().getUserId() : null,
                p.getTeamspaceList() == null ? List.of() :
                        p.getTeamspaceList().stream().map(t -> new TeamspaceResponseDTO(
                                t.getId(),
                                p.getProjectId(),
                                t.getName(),
                                t.getDescription(),
                                t.getCreationDate(),
                                t.getCreator().getUserId()
                        )).collect(Collectors.toList())
        )).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable("id") Integer id) {
        Optional<Project> projectOpt = projectService.getProjectById(id);

        return projectOpt.map(p -> new ResponseEntity<>(
                new ProjectResponseDTO(
                        p.getProjectId(),
                        p.getName(),
                        p.getDescription(),
                        p.getCreatedAt(),
                        p.getCreatedBy() != null ? p.getCreatedBy().getUserId() : null,
                        p.getTeamspaceList() == null ? List.of() :
                                p.getTeamspaceList().stream().map(t -> new TeamspaceResponseDTO(
                                        t.getId(),
                                        p.getProjectId(),
                                        t.getName(),
                                        t.getDescription(),
                                        t.getCreationDate(),
                                        t.getCreator().getUserId()
                                )).collect(Collectors.toList())
                ), HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProjectById(@RequestBody Project project, @PathVariable("id") Integer projectId) {
        project.setProjectId(projectId);

        // Prevent NPE if createdBy is missing
        Integer createdById = project.getCreatedBy() != null ? project.getCreatedBy().getUserId() : null;

        Project updated = projectService.save(project);

        ProjectResponseDTO response = new ProjectResponseDTO(
                updated.getProjectId(),
                updated.getName(),
                updated.getDescription(),
                updated.getCreatedAt(),
                createdById,
                List.of() // You can populate teamspaces if needed
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProjectById(@PathVariable("id") Integer projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
