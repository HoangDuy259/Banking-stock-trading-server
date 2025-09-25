package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.service.user.IUserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;
    @GetMapping("/get-user")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(){
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<UserResponse>("Thành công", userService.getMyInfo()));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }

    }

    @GetMapping("/get-all-users")
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
