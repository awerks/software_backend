package com.seproject.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for development
 * Permits all requests without authentication and explicitly disables default login forms.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Allow all requests without authentication
            )
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF using method reference
            .formLogin(AbstractHttpConfigurer::disable) // Explicitly disable form login
            .httpBasic(AbstractHttpConfigurer::disable); // Explicitly disable HTTP Basic auth
        
        return http.build();
    }
}
