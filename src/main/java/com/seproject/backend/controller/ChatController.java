package com.seproject.backend.controller;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.exceptions.UnauthorizedException;
import com.seproject.backend.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.seproject.backend.service.TeamspaceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;

/*
 * REST Controller for chat-related endpoints
 * Handles chat message creation and retrieval
 */

@RestController
@RequestMapping("/api/teamspaces/{teamspaceId}/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private TeamspaceService teamspaceService;
    @Autowired
    private PagedResourcesAssembler<ChatMessageDTO> pagedResourcesAssembler;
    
    @PostMapping
    public ResponseEntity<ChatMessageDTO> createMessage(
        @PathVariable Integer teamspaceId,
        @Valid @RequestBody ChatMessageDTO messageDTO,
        HttpServletRequest request) {
        
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException("User not authenticated");
        }
        
        if (!teamspaceService.isUserMemberOfTeamspace(userId, teamspaceId)) {
            throw new UnauthorizedException("User is not a member of this teamspace");
        }
        messageDTO.setTeamspaceId(teamspaceId);
        ChatMessageDTO createdMessage = chatService.createMessage(messageDTO, userId);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdMessage);   
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ChatMessageDTO>>> getMessages(
        @PathVariable Integer teamspaceId,
        HttpServletRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException("User not authenticated");
        }
        
        if (!teamspaceService.isUserMemberOfTeamspace(userId, teamspaceId)) {
            throw new UnauthorizedException("User is not a member of this teamspace");
        }
        Page<ChatMessageDTO> messages = chatService.getMessages(teamspaceId, page, size);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(pagedResourcesAssembler.toModel(messages));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(e.getMessage());
    }
}
