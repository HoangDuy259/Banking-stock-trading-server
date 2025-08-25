package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public String register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException.PasswordMismatch("Passwords do not match");
        }

        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new AppException.UserAlreadyExists("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException.UserAlreadyExists("Email already exists");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPhoneNum(request.getPhoneNum());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException.UserNotFound("Email not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException.InvalidPassword("Invalid password");
        }

        return jwtUtils.generateToken(user.getUserName());
    }

    @Override
    public TokenResponse getUserInfo(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new AppException.UserNotFound("User not found"));

        TokenResponse response = new TokenResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPhoneNum(user.getPhoneNum());

        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new AppException.UserNotFound("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}