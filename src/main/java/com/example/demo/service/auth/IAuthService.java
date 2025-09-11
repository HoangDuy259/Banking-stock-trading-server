package com.example.demo.service.auth;

import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.request.auth.LoginRequest;
import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.response.user.UserResponse;

import javax.security.sasl.AuthenticationException;

public interface IAuthService {
    UserResponse register(UserRegisterRequest userRegisterRequest);
    TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException;
    void sendOtp(String email);
    boolean resetPassword(String email, String otp, String newPassword);

}
