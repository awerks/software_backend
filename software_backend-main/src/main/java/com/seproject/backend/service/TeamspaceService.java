package com.seproject.backend.service;

import com.seproject.backend.entity.Project;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.ProjectRepository;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Teamspace createTeamspace(Integer projectId, Teamspace teamspace) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        //TODO: rewrite to work with user_id from session not requestbody
        // now it requires body:
        // {
        //    "creator": {
        //        "userId": 54
        //    },
        //    "name": "Teamfd Alpha",
        //    "description": "Teamspafce description"
        //}
        User user = userRepository.findById(teamspace.getCreator().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        teamspace.setCreator(user);
        teamspace.setProject(project);

        return teamspaceRepository.save(teamspace);
    }

    public void deleteTeamspace(int teamspaceId) {
        teamspaceRepository.deleteById(teamspaceId);
    }

    public Optional<Teamspace> getTeamspaceById(int teamspaceId) {
        return teamspaceRepository.findById(teamspaceId);
    }
}
