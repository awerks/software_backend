package com.seproject.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for chat messages
 * Used for receiving message data from client and sending responses
 */
@Data
public class ChatMessage {
    private Integer messageId;
    
    @NotNull(message = "Teamspace ID cannot be null")
    private Integer teamspaceId;
    
    @NotBlank(message = "Message content cannot be empty")
    private String message;
    
    private Integer senderId;
    private String senderName;
    private String timestamp;
}