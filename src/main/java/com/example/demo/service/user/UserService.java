package com.example.demo.service.user;

import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.UserContextUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserMapper userMapper;
    UserContextUtils userContextUtils;

    @Override
    public UserResponse getMyInfo() {
        User user = userContextUtils.getAuthenticationUsername();

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse updateUser() {
        return null;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        return null;
    }

    @Override
    public boolean isUserEnabled(String username) {
        return false;
    }

}
