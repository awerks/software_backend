package com.seproject.backend.service.impl;

import com.seproject.backend.entity.Project;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.ProjectRepository;
import com.seproject.backend.repository.UserRepository;
import com.seproject.backend.service.ProjectService;
import com.seproject.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProjectServiceImpl
 * 
 * This class provides the concrete implementation of the ProjectService interface.
 * It handles all business logic related to project management, including CRUD operations
 * and additional business rules.
 * 
 * Key features:
 * - CRUD operations implementation
 * - Business logic validation
 * - Error handling
 * - Transaction management
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Project createProject(Project project, Integer creatorId) {
        User creator = userRepository.findById(creatorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + creatorId));
        
        project.setCreatedBy(creator);
        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsByUserId(Long userId) {
        return projectRepository.findByCreatedBy_UserId(userId);
    }

    @Override
    public Project updateProject(Long projectId, Project project) {
        // Verify project exists
        Project existingProject = getProjectById(projectId);
        
        // Update fields
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        
        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        // Verify project exists
        getProjectById(projectId);
        projectRepository.deleteById(projectId);
    }
} 