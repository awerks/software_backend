package com.seproject.backend.service;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.entity.ChatMessage;
import com.seproject.backend.repository.ChatMessageRepository;
import com.seproject.backend.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/*
 * Service layer for chat-related operations
 * Handles business logic for chat message
 */
@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private TeamspaceService teamspaceService;
    

    @Transactional
    public ChatMessageDTO createMessage(ChatMessageDTO messageDTO, Integer userId){
        //Validating teamspace membershipt
        if (!teamspaceService.isUserMemberOfTeamspace(userId, messageDTO.getTeamspaceId())){
            throw new UnauthorizedException("User is not a member of this teamspace");
        }

        //Create and save the message
        ChatMessage message = new ChatMessage();
        message.setTeamspaceId(messageDTO.getTeamspaceId());
        message.setSenderId(userId);
        message.setMessage(messageDTO.getMessage());

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Convert to DTO for response
        return converToDTO(savedMessage);
    }

    public Page<ChatMessageDTO> getMessages(Integer teamspaceId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<ChatMessage> messages = chatMessageRepository.findByTeamspaceId(teamspaceId, pageable);
        return messages.map(this::converToDTO);
    }

    // convert a ChatMessage entity to the ChatMessageDTO

    private ChatMessageDTO converToDTO(ChatMessage message){
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessageId(message.getMessageId());
        dto.setTeamspaceId(message.getTeamspaceId());
        dto.setMessage(message.getMessage());
        dto.setSenderId(message.getSenderId());
        dto.setTimestamp(message.getTimestamp().toString());
        return dto;
    }

    
}
