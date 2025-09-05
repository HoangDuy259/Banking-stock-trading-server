package com.example.demo.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String phoneNum;
    private String password;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastLogin;
    private String confirmPassword;
}
