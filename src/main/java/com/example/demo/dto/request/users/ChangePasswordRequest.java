package com.example.demo.dto.request.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message ="Password is required")
    private String password;
}
