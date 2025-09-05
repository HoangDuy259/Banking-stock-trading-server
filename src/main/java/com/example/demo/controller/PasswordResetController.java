package com.example.demo.controller;

import com.example.demo.service.impl.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestParam String email) {
        passwordResetService.sendOtp(email);
        return ResponseEntity.ok("OTP đã được gửi về email");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String otp,
                                           @RequestParam String newPassword) {
        boolean success = passwordResetService.resetPassword(email, otp, newPassword);
        if (!success) return ResponseEntity.badRequest().body("OTP sai hoặc hết hạn");
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }
}

