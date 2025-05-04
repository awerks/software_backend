package com.seproject.backend.controller;

import com.seproject.backend.annotations.RoleRequired;
import com.seproject.backend.dto.CreateProjectRequest;
import com.seproject.backend.dto.ErrorResponse;
import com.seproject.backend.dto.CreateProjectResponse;
import com.seproject.backend.dto.TeamspaceResponseDTO;
import com.seproject.backend.dto.UpdateProjectRequest;
import com.seproject.backend.dto.UpdateProjectResponse;
import com.seproject.backend.entity.Project;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.UserRepository;
import com.seproject.backend.service.ProjectService;
import com.seproject.backend.util.SessionUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

        @Autowired
        private ProjectService projectService;
        @Autowired
        private UserRepository userRepository;

        @PostMapping
        @RoleRequired("project_manager")
        public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectRequest request) {

                Optional<User> userOpt = userRepository.findById(request.getCreatedBy());
                if (userOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("User not found"));
                }
                User user = userOpt.get();

                Project project = new Project();
                project.setName(request.getName());
                project.setDescription(request.getDescription());
                project.setCreatedBy(user);

                Project saved = projectService.save(project);

                CreateProjectResponse response = new CreateProjectResponse(
                                saved.getProjectId(),
                                saved.getName(),
                                saved.getDescription(),
                                saved.getCreatedAt(),
                                saved.getCreatedBy().getUserId(),
                                List.of() // No teamspaces created yet
                );

                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @GetMapping
        @RoleRequired("project_manager")
        public ResponseEntity<?> getAllProjects(@NonNull HttpServletRequest request) {
                List<Project> projects = projectService.getAllProjectsForGivenUser(SessionUtil.getUserId(request));
                if (projects.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("No projects found"));
                }
                List<CreateProjectResponse> dtos = projects.stream().map(p -> new CreateProjectResponse(
                                p.getProjectId(),
                                p.getName(),
                                p.getDescription(),
                                p.getCreatedAt(),
                                p.getCreatedBy().getUserId(),
                                p.getTeamspaceList() == null ? List.of()
                                                : p.getTeamspaceList().stream().map(t -> new TeamspaceResponseDTO(
                                                                t.getTeamspaceId(),
                                                                p.getProjectId(),
                                                                t.getName(),
                                                                t.getDescription(),
                                                                t.getCreationDate(),
                                                                t.getCreator().getUserId()))
                                                                .collect(Collectors.toList())))
                                .collect(Collectors.toList());

                return new ResponseEntity<>(dtos, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        @RoleRequired("project_manager")
        public ResponseEntity<?> getProjectById(@PathVariable("id") Integer id,
                        @NonNull HttpServletRequest request) {
                Optional<Project> projectOpt = projectService.getProjectById(id);
                if (projectOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Project not found"));
                }
                if (!"admin".equals(SessionUtil.getRole(request))
                                && projectOpt.get().getCreatedBy().getUserId() != SessionUtil.getUserId(request)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(new ErrorResponse("You are not allowed to view this project"));
                }

                return projectOpt.map(p -> new ResponseEntity<>(
                                new CreateProjectResponse(
                                                p.getProjectId(),
                                                p.getName(),
                                                p.getDescription(),
                                                p.getCreatedAt(),
                                                p.getCreatedBy().getUserId(),
                                                p.getTeamspaceList() == null ? List.of()
                                                                : p.getTeamspaceList().stream()
                                                                                .map(t -> new TeamspaceResponseDTO(
                                                                                                t.getTeamspaceId(),
                                                                                                p.getProjectId(),
                                                                                                t.getName(),
                                                                                                t.getDescription(),
                                                                                                t.getCreationDate(),
                                                                                                t.getCreator().getUserId()))
                                                                                .collect(Collectors.toList())),
                                HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PutMapping("/{id}")
        @RoleRequired("project_manager")
        public ResponseEntity<?> updateProjectById(@Valid @RequestBody UpdateProjectRequest project,
                        @PathVariable("id") Integer projectId, @NonNull HttpServletRequest request) {
                Optional<Project> projectOpt = projectService.getProjectById(projectId);
                if (projectOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("Project not found"));
                }
                if (!"admin".equals(SessionUtil.getRole(request))
                                && projectOpt.get().getCreatedBy().getUserId() != SessionUtil.getUserId(request)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(new ErrorResponse("You are not allowed to modify this project"));
                }
                Project existingProject = projectOpt.get();
                existingProject.setName(project.getName());
                existingProject.setDescription(project.getDescription());
                projectService.save(existingProject);

                UpdateProjectResponse response = new UpdateProjectResponse();
                response.setProjectId(existingProject.getProjectId());
                response.setName(existingProject.getName());
                response.setDescription(existingProject.getDescription());
                response.setCreationDate(existingProject.getCreatedAt());
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        @RoleRequired("project_manager")
        public ResponseEntity<Object> deleteProjectById(@PathVariable("id") Integer projectId,
                        @NonNull HttpServletRequest request) {
                Optional<Project> projectOpt = projectService.getProjectById(projectId);
                if (projectOpt.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(new ErrorResponse("Project not found"));
                }
                if (!"admin".equals(SessionUtil.getRole(request))
                                && projectOpt.get().getCreatedBy().getUserId() != SessionUtil.getUserId(request)) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(new ErrorResponse("You are not allowed to delete this project"));
                }
                projectService.deleteProjectById(projectId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
