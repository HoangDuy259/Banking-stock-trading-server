package com.example.demo.service.auth;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OtpRedisService {
    private final StringRedisTemplate redisTemplate;

    public OtpRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveOtp(String email, String otp, long ttlMinutes) {
        String key = "OTP:" + email;
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(ttlMinutes));
    }

    public String getOtp(String email) {
        String key = "OTP:" + email;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteOtp(String email) {
        String key = "OTP:" + email;
        redisTemplate.delete(key);
    }
}

