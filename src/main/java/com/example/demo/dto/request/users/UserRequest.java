package com.example.demo.dto.request.users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNum;

    @NotBlank(message ="Password is required")
    private String password;

    @NotBlank(message ="Created at is required")
    private OffsetDateTime createdAt;

    private OffsetDateTime lastLogin;
}
