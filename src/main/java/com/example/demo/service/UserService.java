package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.request.users.UserRequest;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    String register(RegisterRequest request);
    UserResponse login(LoginRequest request);
    TokenResponse getUserInfo(String username);
    User updateUser(UUID id, UserRequest user);
}