package com.seproject.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="reset_confirm_tokens")
public class Token{

    @Id
    @Column(name="token")
    private String token;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name="expires_at_utc", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used;

    public void setToken(String token) {
        this.token=token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public boolean IsExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean getUsed() {
        return used;
    }
}
