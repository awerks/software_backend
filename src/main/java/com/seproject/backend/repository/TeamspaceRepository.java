package com.seproject.backend.repository;

import com.seproject.backend.entity.Teamspace;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamspaceRepository extends JpaRepository<Teamspace, Integer> {
    Optional<Teamspace> findByName(String name);

    Optional<Teamspace> findByTeamspaceId(Integer teamspaceId);
}
