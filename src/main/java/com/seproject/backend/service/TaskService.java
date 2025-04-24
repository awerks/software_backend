package com.seproject.backend.service;

import com.seproject.backend.entity.*;
import com.seproject.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: add the rest of the logic
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TeamspaceRepository teamspaceRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       TeamspaceRepository teamspaceRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.teamspaceRepository = teamspaceRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Integer teamspaceId, Task task) {
        Teamspace teamspace = teamspaceRepository.findById(teamspaceId)
                .orElseThrow(() -> new RuntimeException("Teamspace not found"));

        User user = userRepository.findById(teamspace.getCreator().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //TODO: rewrite to work with user_id from session not requestbody
        // now it requires additional fields in body:
        // {
        //    "creator": {
        //        "userId": 54
        //    },
        //}

        task.setTeamspaces(List.of(teamspace));
        task.setCreator(user);

        return taskRepository.save(task);
    }
}
