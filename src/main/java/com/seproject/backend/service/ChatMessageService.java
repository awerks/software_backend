package com.seproject.backend.service;

import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.dto.UserDTO;
import com.seproject.backend.entity.ChatMessage;
import com.seproject.backend.model.Teamspace;
import com.seproject.backend.entity.User;
import com.seproject.backend.repository.ChatMessageRepository;
import com.seproject.backend.repository.TeamspaceRepository;
import com.seproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ChatMessageService
 * 
 * This service handles all business logic related to chat messages.
 * It provides methods for creating and retrieving chat messages.
 * 
 * Key features:
 * - Message creation
 * - Message retrieval
 * - DTO conversion
 * - Transaction management
 */
@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private TeamspaceRepository teamspaceRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new chat message
     * @param messageDTO The message data
     * @param senderId The ID of the message sender
     * @return The created message as DTO
     */
    @Transactional
    public ChatMessageDTO createMessage(ChatMessageDTO messageDTO, Long senderId) {
        Teamspace teamspace = teamspaceRepository.findById(messageDTO.getTeamspaceId())
            .orElseThrow(() -> new RuntimeException("Teamspace not found"));
        
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessage message = new ChatMessage();
        message.setTeamspace(teamspace);
        message.setSender(sender);
        message.setMessage(messageDTO.getMessage());

        ChatMessage savedMessage = chatMessageRepository.save(message);
        return convertToDTO(savedMessage);
    }

    /**
     * Retrieves all messages for a specific teamspace
     * @param teamspaceId The ID of the teamspace
     * @return List of chat messages as DTOs
     */
    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getMessagesByTeamspace(Long teamspaceId) {
        return chatMessageRepository.findByTeamspace_TeamspaceIdOrderByTimestampDesc(teamspaceId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Converts a ChatMessage entity to a ChatMessageDTO
     * @param message The chat message entity
     * @return The chat message DTO
     */
    private ChatMessageDTO convertToDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessageId(message.getMessageId());
        dto.setMessage(message.getMessage());
        dto.setTimestamp(message.getTimestamp());
        dto.setTeamspaceId(message.getTeamspace().getId());
        
        // Convert sender to UserDTO
        User sender = message.getSender();
        UserDTO senderDTO = new UserDTO();
        senderDTO.setUserId(sender.getUserId());
        senderDTO.setFirstName(sender.getFirstName());
        senderDTO.setLastName(sender.getLastName());
        senderDTO.setEmail(sender.getEmail());
        senderDTO.setUsername(sender.getUsername());
        senderDTO.setBirthdate(sender.getBirthdate());
        senderDTO.setRole(sender.getRole());
        senderDTO.setVerified(sender.isVerified());
        dto.setSender(senderDTO);
        
        return dto;
    }
} 