package com.example.demo.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String phoneNum;
    private String password;
    private String confirmPassword;
}
