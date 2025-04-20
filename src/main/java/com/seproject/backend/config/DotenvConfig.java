package com.seproject.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Map;


@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        try {
            Dotenv dotenv = Dotenv.configure().load();
            
            // Get the map of environment variables
            Map<String, String> env = System.getenv();
            
            // Using reflection to make the environment variables map mutable
            Class<?> clazz = env.getClass();
            Field field = clazz.getDeclaredField("m");
            field.setAccessible(true);
            
            @SuppressWarnings("unchecked")
            Map<String, String> writableEnv = (Map<String, String>) field.get(env);
            
            // Add all variables from .env file to system environment
            dotenv.entries().forEach(e -> writableEnv.put(e.getKey(), e.getValue()));
            
        } catch (Exception e) {
            // Log exception but don't fail startup
            System.err.println("Error loading .env file: " + e.getMessage());
        }
    }
} 