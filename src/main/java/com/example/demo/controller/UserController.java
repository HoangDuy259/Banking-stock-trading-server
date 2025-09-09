package com.example.demo.controller;

import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.request.auth.LoginRequest;
import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(){
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<UserResponse>("Thành công", userService.getMyInfo()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<UserResponse>> getAllUsers(){
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<UserResponse>("Thành công", userService.getMyInfo()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    }
    @GetMapping("/update")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyInfo(){
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<UserResponse>("Thành công", userService.getMyInfo()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(){
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<UserResponse>("Thành công", userService.getMyInfo()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    }

}
