package com.seproject.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * Data Transfer Object for chat message
 * Used for receiving messages data from client and sending responses
 * 
 */
@Data
public class ChatMessageDTO {
    private Integer messageId;

    @NotNull(message = "Teamspace ID cannot be null")
    private Integer teamspaceId;

    @NotBlank(message = "Message content cannot be empty")
    private String message;

    private Integer senderId;
    private String senderName;
    private String timestamp;

}