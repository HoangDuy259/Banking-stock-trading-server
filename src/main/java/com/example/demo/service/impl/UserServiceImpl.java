package com.example.demo.service.impl;


import com.example.demo.dto.identity.ClientTokenExchangeParam;
import com.example.demo.dto.identity.KeycloakProvider;
import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.request.auth.LoginRequest;
import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.IdentityClient;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    KeycloakProvider keycloakProvider;
    IdentityClient identityClient;
    PasswordEncoder passwordEncoder;

//    public UserResponse register(UserRegisterRequest userRegisterRequest) {
//        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
//            throw new IllegalArgumentException("Username đã tồn tại");
//        }
//
//        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
//            throw new IllegalArgumentException("Email đã tồn tại");
//        }
//
//        log.info("in a method");
//
//        User user = userMapper.toUser(userRegisterRequest);
//        log.info("user: " + userRegisterRequest.getUsername());
//        log.info("user: " + user.getUsername());
//        log.info("email: " + user.getEmail());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        log.info("password: " + user.getPassword());
//        user.setEnabled(true);
//        log.info("register successed: " + userRepository.count());
//        userRepository.save(user);
//
//        return userMapper.toUserResponse(user);
//    }


//    public TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException {
//        User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow( () ->
//                 new AuthenticationException("User not found")
//        );
//        log.info("user: "+ user.getUsername());
//
//        // Kiểm tra nếu tài khoản bị vô hiệu hóa
//        if (!user.getEnabled()) {
//            throw new DisabledException("Tài khoản chưa được kích hoạt.");
//        }
//
//        // Gửi request đến Keycloak để lấy token
//        return identityClient.exchangeTokenClient(ClientTokenExchangeParam.builder()
//                .grant_type("password")
//                .client_id(keycloakProvider.getClientID())
//                .client_secret(keycloakProvider.getClientSecret())
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .scope("openid")
//                .build());
//    }

    @Override
    public UserResponse getMyInfo() {
        String username = getAuthenticationUsername();

        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));

        return userMapper.toUserResponse(user);
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).collect(Collectors.toList());
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

    private String getAuthenticationUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String sub = jwt.getSubject(); // tương đương getClaim("sub")
            username = jwt.getClaimAsString("preferred_username");

            System.out.println("sub = " + sub);
            System.out.println("username = " + username);
        }
        return username;
    }

//    Duong code

}
