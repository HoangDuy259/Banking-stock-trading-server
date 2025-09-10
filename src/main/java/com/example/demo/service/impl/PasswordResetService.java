package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordResetService {
    private final UserRepository userRepository;
    private final OtpRedisService otpRedisService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository,
                                OtpRedisService otpRedisService,
                                EmailService emailService,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.otpRedisService = otpRedisService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu OTP vào Redis với TTL 5 phút
        otpRedisService.saveOtp(user.getEmail(), otp, 5);

        // Gửi email OTP
        emailService.sendOtpEmail(user.getEmail(), otp);
    }

    public boolean resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        String cachedOtp = otpRedisService.getOtp(email);

        if (cachedOtp == null || !cachedOtp.equals(otp)) {
            return false; // OTP sai hoặc đã hết hạn
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpRedisService.deleteOtp(email);
        return true;
    }
}
