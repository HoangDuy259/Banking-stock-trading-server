package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RefreshTokenResponse;
import com.example.demo.dto.response.TokenResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.AppException;
import com.example.demo.security.JwtUtils;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
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

//    Duong code ne
//    cấp lại access token khi hết hạn bằng refresh token đang có
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

//        if (!jwtUtils.validateToken(refreshToken)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

        if (!jwtUtils.validateToken(refreshToken, "refresh")) {
            throw new AppException.InvalidToken("Invalid refresh token");
        }

        String username = jwtUtils.getUsernameFromToken(refreshToken);

        UserDetails userDetails = userService.loadUserByUsername(username);

        // tạo access token mới
        String newAccessToken = jwtUtils.generateToken(userDetails.getUsername());

        RefreshTokenResponse response = new RefreshTokenResponse(
                newAccessToken,
                jwtUtils.getAccessTokenExpiration()
        );

        return ResponseEntity.ok(response);
    }

}