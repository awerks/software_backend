package com.seproject.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * ChatMessageDTO (Data Transfer Object)
 * 
 * This DTO is used to transfer chat message data between the client and server.
 * It contains only the necessary fields for message display and creation.
 * 
 * Key features:
 * - Message ID
 * - Message content
 * - Timestamp
 * - Sender information
 * - Teamspace ID
 */
@Data
public class ChatMessageDTO {
    private Long messageId;
    private String message;
    private LocalDateTime timestamp;
    private UserDTO sender;
    private Long teamspaceId;
} 