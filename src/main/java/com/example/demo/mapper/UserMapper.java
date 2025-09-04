package com.example.demo.mapper;


import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRegisterRequest request);

    UserResponse toUserResponse(User user);
}
