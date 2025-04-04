package com.seproject.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserPayload {
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private String username;
    private String role;
    private SuccessResponse successResponse;
}
