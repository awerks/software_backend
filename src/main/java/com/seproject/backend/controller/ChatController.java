package com.seproject.backend.controller;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.exceptions.UnauthorizedException;
import com.seproject.backend.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.seproject.backend.service.TeamspaceService;

/*
 * REST Controller for chat-related endpoints
 * Handles chat message creation and retrieval
 */

 @RestController
 @RequestMapping("/api/teamspaces/{teamspaceId}/chat")

public class ChatController {
    @Autowired
    private ChatService chatService;
    private TeamspaceService teamspaceService;
    
    @PostMapping
    public ResponseEntity<ChatMessageDTO> createMessage(
        @PathVariable Integer teamspaceId,
        @Valid @RequestBody ChatMessageDTO messageDTO,
        @AuthenticationPrincipal Integer userId){
    
            messageDTO.setTeamspaceId(teamspaceId);
            ChatMessageDTO createMessage = chatService.createMessage(messageDTO, userId);
            return ResponseEntity.ok(createMessage);   
    }
    @GetMapping
    public ResponseEntity<Page<ChatMessageDTO>> getMessages(
        @PathVariable Integer teamspaceId,
        @AuthenticationPrincipal Integer userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size){
            //Validate user's access rights
        if (!teamspaceService.isUserMemberOfTeamspace(userId, teamspaceId)){
            throw new UnauthorizedException("User is not a member of this teamspace");
        }
        Page<ChatMessageDTO> message = chatService.getMessages(teamspaceId, page, size);
        return ResponseEntity.ok(message);
        }

}
