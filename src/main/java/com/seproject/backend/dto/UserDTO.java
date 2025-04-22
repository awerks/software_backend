package com.seproject.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private LocalDateTime birthdate;
    private String role;
    private boolean verified;
} 