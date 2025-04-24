package com.seproject.backend.repository;

import com.seproject.backend.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Integer> {

    List<TaskComment> findByTask_TaskId(Integer taskId);
}
