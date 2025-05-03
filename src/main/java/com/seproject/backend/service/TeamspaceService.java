package com.seproject.backend.service;

import com.seproject.backend.dto.TeamspaceCreationRequest;
import com.seproject.backend.entity.Project;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.exceptions.BadRequestException;
import com.seproject.backend.exceptions.ForbiddenException;
import com.seproject.backend.exceptions.ResourceNotFoundException;
import com.seproject.backend.repository.ProjectRepository;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class TeamspaceService {

    private final TeamspaceRepository teamspaceRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TeamspaceService(TeamspaceRepository teamspaceRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {
        this.teamspaceRepository = teamspaceRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Teamspace createTeamspace(Integer projectId, Integer creatorId, TeamspaceCreationRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BadRequestException("This project does not exists"));

        if (teamspaceRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("This teamspace name is already been used");
        }

        Teamspace newTeamspace = new Teamspace();
        newTeamspace.setName(request.getName());
        newTeamspace.setProject(project);
        newTeamspace.setDescription(request.getDescription());

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        newTeamspace.setCreator(creator);

        return teamspaceRepository.save(newTeamspace);
    }

    public void deleteTeamspace(Integer teamspaceId, Integer actorId) {
        Teamspace teamspace = teamspaceRepository.findById(teamspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Teamspace not found"));

        if (!teamspace.getCreator().getUserId().equals(actorId)) {
            throw new ForbiddenException("Forbidden (insufficient privileges)");
        }

        teamspaceRepository.delete(teamspace);
    }
}
