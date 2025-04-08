package com.seproject.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:dummy@example.com}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        // Just log the email for development instead of sending it
        logger.info("Sending email to: {}", to);
        logger.info("Subject: {}", subject);
        logger.info("Body: {}", body);
        
        // Only use the actual mail sender if it's configured
        if (mailSender != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }
    }

    public void sendResetPasswordEmail(String to, String token) {
        String subject = "Password Reset Link";
        String body = "Hello,\n\n" +
                "You requested to reset your password. Click the link below to proceed:\n" +
                "https://se-project.up.railway.app/reset-password?token=" + token + "\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Sincerely,\nDashpress";
        sendEmail(to, subject, body);
    }

    public void sendVerificationEmail(String to, String token) {
        String subject = "Email Verification";
        String body = "Hello,\n\n" +
                "Please verify your email address by clicking the link below:\n" +
                "https://se-project.up.railway.app/verify-email?token=" + token + "\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Sincerely,\nDashpress";
        sendEmail(to, subject, body);
    }
}
