package com.seproject.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "reset_confirm_tokens")
@Data
public class Token {

    @Id
    @Column(name = "token", length = 255)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at_utc", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used", nullable = false)
    private boolean used;

    public boolean isExpired() {
        return LocalDateTime.now(ZoneId.of("UTC")).isAfter(expiresAt);
    }

}
