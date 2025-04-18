package com.seproject.backend.repository;

import com.seproject.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByTeamspaceIdOrderByTimestampDesc(Integer teamspaceId);
} 