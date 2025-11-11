package com.example.demo.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPassword {
    @NotBlank(message = "Email is REQUIRED")
    @Email(message = "Email is invalid")
    @Size(max = 100, message = "Email limit is 100 characters")
    String email;

    @NotBlank(message = "Password is REQUIRED")
    @Size(max = 50, message = "Password limit is 50 characters")
    String newPassword;

    @NotBlank(message = "Otp is REQUIRED")
    String otp;
}
