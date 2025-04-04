package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ChatMessage Entity
 * 
 * This entity represents a chat message in the system.
 * It is associated with a teamspace and a sender (user).
 * 
 * Key features:
 * - Unique message ID
 * - Message content
 * - Timestamp
 * - Many-to-One relationship with Teamspace
 * - Many-to-One relationship with User (sender)
 */
@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamspace_id", nullable = false)
    private Teamspace teamspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
} 