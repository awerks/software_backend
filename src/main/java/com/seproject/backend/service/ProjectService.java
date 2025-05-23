package com.seproject.backend.service;

import com.seproject.backend.entity.Project;
import com.seproject.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjectsForGivenUser(Integer userId) {
        return projectRepository.findAllByCreatedBy_UserId(userId);
    }

    public Optional<Project> getProjectById(Integer projectId) {
        return projectRepository.findById(projectId);
    }

    public void deleteProjectById(Integer projectId) {
        projectRepository.deleteById(projectId);
    }
}
