package com.example.demo.controller;

import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.request.auth.ForgotPassword;
import com.example.demo.dto.request.auth.LoginRequest;
import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

//    đăng ký
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse<>("Đăng ký thành công",
                            authService.register(userRegisterRequest)));
        }catch (IllegalArgumentException  e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Lỗi đăng ký", null));
        }
    }

//    đăng nhập
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenExchangeResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            TokenExchangeResponse tokenResponse = authService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>("Đăng nhập thành công", tokenResponse));
        } catch (AuthenticationException | DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verify(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            authService.verifyCredentials(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new ApiResponse<>("Đăng nhập thành công", true));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(e.getMessage(), false));
        }
    }


    //    gửi otp
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestParam String email) {
        authService.sendOtp(email);
        return ResponseEntity.ok("OTP đã được gửi về email");
    }

//    reset password
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ForgotPassword request) {
        System.out.println("controller data: " + request);
        boolean success = authService.resetPassword(request);
        if (!success) return ResponseEntity.badRequest().body("OTP sai hoặc hết hạn");
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }




}
