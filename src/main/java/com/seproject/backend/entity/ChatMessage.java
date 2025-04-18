package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/*
 * Entity representing a chat message in the database
 * Maps to the chat_messages table
 * 
 */

 @Entity
 @Table(name = "chat_messages")
 @Data

 public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "teamspace_id", nullable = false)
    private Integer teamspaceId;

    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate(){
        timestamp = LocalDateTime.now();
    }
 }