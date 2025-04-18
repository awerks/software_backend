package com.seproject.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.seproject.backend.repository.TeamspaceMemberRepository;

/**
 * Service for handling teamspace-related operations
 * Currently implements member validation for chat functionality
 */

@Service
public class TeamspaceService {

    @Autowired
    private TeamspaceMemberRepository teamspaceMemberRepository;

    
    @Transactional(readOnly = true)
    public boolean isUserMemberOfTeamspace(Integer userId, Integer teamspaceId) {
        return teamspaceMemberRepository.existsByUserIdAndTeamspaceId(userId, teamspaceId);
    }
} 