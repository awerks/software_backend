package com.seproject.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Profile("dev")
public class DevConfig {
    
    /**
     * Provides a no-op mail sender for development environments
     */
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl(); // Empty implementation that won't actually send emails
    }
} 