package com.example.demo.service.user;


import com.example.demo.dto.response.user.UserResponse;

import java.util.List;


public interface IUserService {
    UserResponse getMyInfo();
    List<UserResponse> getAllUsers();
    UserResponse updateUser();
//    UserResponse updateUser(UpdateUserRequest request);
    Boolean deleteUser(Long userId);
//    Boolean resetPassword(ResetPasswordRequest newPassword);
    boolean isUserEnabled(String username);
    boolean changePassword(String password, String email);
}