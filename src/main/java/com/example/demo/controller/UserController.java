package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(new UserResponse(token));
    }

    @GetMapping("/info")
    public ResponseEntity<TokenResponse> getUserInfo(@RequestHeader("Authorization") String token) {
        // Xóa "Bearer " từ token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Lấy username từ token
        String username = JwtUtils.getUsernameFromToken(token);

        // Lấy thông tin user
        TokenResponse userInfo = userService.getUserInfo(username);
        return ResponseEntity.ok(userInfo);
    }

}