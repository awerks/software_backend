package com.seproject.backend.repository;

import com.seproject.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByTeamspaceIdOrderByTimestampDesc(Integer teamspaceId);
    Page<ChatMessage> findByTeamspaceId(Integer teamspaceId, Pageable pageable);
} 