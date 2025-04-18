package com.seproject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.seproject.backend.entity.TeamspaceMember;

/**
 * Repository interface for teamspace member operations
 */
@Repository
public interface TeamspaceMemberRepository extends JpaRepository<TeamspaceMember, Integer> {
    boolean existsByUserIdAndTeamspaceId(Integer userId, Integer teamspaceId);
} 