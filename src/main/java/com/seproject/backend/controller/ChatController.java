package com.seproject.backend.controller;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
 * REST Controller for chat-related endpoints
 * Handles chat message creation and retrieval
 */

 @RestController
 @RequestMapping("/api/teamspaces/{teamspaceId}/chat")

public class ChatController {
    @Autowired
    private ChatService chatService;
    
    @PostMapping
    public ResponseEntity<ChatMessageDTO> createMessage(
        @PathVariable Integer teamspaceId,
        @Valid @RequestBody ChatMessageDTO messageDTO,
        @AuthenticationPrincipal Integer userId){
    
            messageDTO.setTeamspaceId(teamspaceId);
            ChatMessageDTO createMessage = chatService.createMessage(messageDTO, userId);
            return ResponseEntity.ok(createMessage);
        
    }
}
