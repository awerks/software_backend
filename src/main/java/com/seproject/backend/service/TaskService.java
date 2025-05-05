package com.seproject.backend.service;

import com.seproject.backend.dto.CreateTaskRequest;
import com.seproject.backend.dto.UpdateTaskRequest;
import com.seproject.backend.entity.Task;
import com.seproject.backend.entity.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.exceptions.ResourceNotFoundException;
import com.seproject.backend.repository.TaskRepository;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final TeamspaceRepository teamspaceRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo,
                       TeamspaceRepository teamspaceRepo,
                       UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.teamspaceRepo = teamspaceRepo;
        this.userRepo = userRepo;
    }


    public Task createTask(Integer teamspaceId, CreateTaskRequest req, Integer creatorId) {
        Teamspace ts = teamspaceRepo.findById(teamspaceId)
            .orElseThrow(() -> new ResourceNotFoundException("Teamspace not found"));
    
        User creator = userRepo.findById(creatorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
        Task t = new Task();
        t.setName(req.getName());
        t.setDeadline(req.getDeadline());
        t.setDescription(req.getDescription());
        t.setCreator(creator); 
        t.setTeamspaces(List.of(ts));
        return taskRepo.save(t);
    }


    public Task updateTask(Integer taskId, UpdateTaskRequest req) {
        Task existing = taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        existing.setName(req.getName());
        existing.setDeadline(req.getDeadline());
        existing.setDescription(req.getDescription());
        existing.setStatus(req.getStatus());

        return taskRepo.save(existing);
    }


    public void assignUsers(Integer taskId, List<Integer> userIds) {
        Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        List<User> users = userRepo.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new ResourceNotFoundException("One or more users not found");
        }
        task.setAssignees(users);
        taskRepo.save(task);
    }


    public void associateTeamspaces(Integer taskId, List<Integer> tsIds) {
        Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        List<Teamspace> tsList = teamspaceRepo.findAllById(tsIds);
        if (tsList.size() != tsIds.size()) {
            throw new ResourceNotFoundException("One or more teamspaces not found");
        }
        task.setTeamspaces(tsList);
        taskRepo.save(task);
    }

    public void deleteTask(Integer taskId) {
        Task t = taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepo.delete(t);
    }

    public Task getTaskById(Integer taskId) {
        return taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}



        // 3) ROLE CHECK: only PROJECT_MANAGERs or TEAMLEADs of this space
        //boolean isProjectManager = "PROJECT_MANAGER".equalsIgnoreCase(requesterRole);
        //boolean isLeadOfThisTs   =
         //   "TEAMLEAD".equalsIgnoreCase(requesterRole)
         //&& ts.getCreator().getUserId().equals(requesterId);

        //if (!isProjectManager && !isLeadOfThisTs) {
       //     throw new AccessDeniedException("Not allowed to create tasks in this teamspace");
       // }