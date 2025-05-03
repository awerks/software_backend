package com.seproject.backend.repository;

import java.util.Optional;

import com.seproject.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByProjectId(Integer projectId);

    List<Project> findAllByCreatedBy_UserId(Integer userId);
}
