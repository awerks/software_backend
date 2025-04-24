package com.seproject.backend.service;

import com.seproject.backend.entity.Task;
import com.seproject.backend.entity.TaskComment;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.TaskCommentRepository;
import com.seproject.backend.repository.TaskRepository;
import com.seproject.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskCommentService(TaskCommentRepository taskCommentRepository,
                              TaskRepository taskRepository,
                              UserRepository userRepository) {
        this.taskCommentRepository = taskCommentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskComment createComment(TaskComment taskComment) {
        Task task = taskRepository.findById(taskComment.getTask().getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(taskComment.getCreator().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        taskComment.setCreator(user);
        taskComment.setTask(task);
        return taskCommentRepository.save(taskComment);
    }

    public List<TaskComment> getCommentsForTask(Integer taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskCommentRepository.findByTask_TaskId(taskId);
    }

    public TaskComment getCommentById(Integer commentId) {
        return taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public TaskComment updateComment(Integer commentId, TaskComment updatedComment) {
        TaskComment existingComment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        existingComment.setContent(updatedComment.getContent());
        existingComment.setCreatedAt(updatedComment.getCreatedAt());

        return taskCommentRepository.save(existingComment);
    }

    public void deleteComment(Integer commentId) {
        TaskComment comment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        taskCommentRepository.delete(comment);
    }
}
