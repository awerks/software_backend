package com.seproject.backend.controller;

import com.seproject.backend.entity.TaskComment;
import com.seproject.backend.service.TaskCommentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskCommentController {

    private final TaskCommentService commentService;

    public TaskCommentController(TaskCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody TaskComment comment) {
        try {
            TaskComment createdComment = commentService.createComment(comment);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("error", "Error creating comment"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<?> getCommentsForTask(@PathVariable Integer taskId) {
        try {
            List<TaskComment> comments = commentService.getCommentsForTask(taskId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error retrieving comments"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Integer commentId) {
        try {
            TaskComment comment = commentService.getCommentById(commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Comment not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer commentId, @RequestBody TaskComment updatedComment) {
        try {
            TaskComment comment = commentService.updateComment(commentId, updatedComment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error updating comment"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(Map.of("message", "Comment deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error deleting comment"), HttpStatus.BAD_REQUEST);
        }
    }
}
