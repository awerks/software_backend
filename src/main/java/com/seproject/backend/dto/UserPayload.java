package com.seproject.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserPayload {
    private int user_id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthdate;
    private String username;
    private String role;
    private SuccessResponse successResponse;
}
