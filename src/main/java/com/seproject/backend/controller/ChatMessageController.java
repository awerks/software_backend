package com.seproject.backend.controller;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ChatMessageController
 * 
 * This controller handles HTTP requests related to chat messages.
 * It provides endpoints for creating and retrieving chat messages.
 * 
 * Key features:
 * - POST endpoint for creating messages
 * - GET endpoint for retrieving messages
 * - Proper response handling
 * - Input validation
 */
@RestController
@RequestMapping("/api/teamspaces/{teamspaceId}/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * Creates a new chat message in a teamspace
     * @param teamspaceId The ID of the teamspace
     * @param messageDTO The message data
     * @param senderId The ID of the message sender (from authentication)
     * @return The created message
     */
    @PostMapping
    public ResponseEntity<ChatMessageDTO> createMessage(
            @PathVariable Long teamspaceId,
            @RequestBody ChatMessageDTO messageDTO,
            @RequestHeader("X-User-ID") Long senderId) {
        
        messageDTO.setTeamspaceId(teamspaceId);
        ChatMessageDTO createdMessage = chatMessageService.createMessage(messageDTO, senderId);
        return ResponseEntity.ok(createdMessage);
    }

    /**
     * Retrieves all messages for a specific teamspace
     * @param teamspaceId The ID of the teamspace
     * @return List of chat messages
     */
    @GetMapping
    public ResponseEntity<List<ChatMessageDTO>> getMessages(
            @PathVariable Long teamspaceId) {
        
        List<ChatMessageDTO> messages = chatMessageService.getMessagesByTeamspace(teamspaceId);
        return ResponseEntity.ok(messages);
    }
} 