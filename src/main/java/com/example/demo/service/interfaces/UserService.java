//package com.example.demo.service.interfaces;
//
//
//import com.example.demo.dto.identity.TokenExchangeResponse;
//import com.example.demo.dto.request.auth.LoginRequest;
//import com.example.demo.dto.request.auth.UserRegisterRequest;
//import com.example.demo.dto.response.user.UserResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.security.sasl.AuthenticationException;
//import java.util.List;
//
//
//public interface UserService  {
//    UserResponse getMyInfo();
//    List<UserResponse> getAllUsers();
//    UserResponse updateUser();
////    UserResponse updateUser(UpdateUserRequest request);
//    Boolean deleteUser(Long userId);
////    Boolean resetPassword(ResetPasswordRequest newPassword);
//    boolean isUserEnabled(String username);
//}