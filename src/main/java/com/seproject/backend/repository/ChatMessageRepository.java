package com.seproject.backend.repository;

import com.seproject.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ChatMessageRepository
 * 
 * This repository handles all database operations related to chat messages.
 * It extends JpaRepository to provide basic CRUD operations and custom query methods.
 * 
 * Key features:
 * - Basic CRUD operations inherited from JpaRepository
 * - Custom query methods for message retrieval
 * - Pagination support
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    /**
     * Retrieves all messages for a specific teamspace, ordered by timestamp
     * @param teamspaceId The ID of the teamspace
     * @return List of chat messages
     */
    List<ChatMessage> findByTeamspace_IdOrderByTimestampDesc(Long teamspaceId);
} 