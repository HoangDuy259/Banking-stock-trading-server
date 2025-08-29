package com.example.demo.service.impl;

import com.example.demo.dto.response.CustomOAuth2User;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public CustomOAuth2UserService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");

        // Tìm hoặc tạo user trong DB
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUserName(username);
                    newUser.setEmail(email);
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });

        // Tạo JWT token
        String accessToken = jwtUtils.generateToken(user.getUserName());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUserName());

        // Lưu token vào OAuth2User attributes (hoặc trả về qua API)
        return new CustomOAuth2User(oAuth2User, accessToken, refreshToken);
    }
}
