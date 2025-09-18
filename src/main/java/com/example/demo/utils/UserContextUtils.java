package com.example.demo.utils;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserContextUtils {
    UserRepository userRepository;
    //get current user through jwt bearer token
    public User getAuthenticationUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            String username = jwtAuth.getToken().getClaimAsString("preferred_username");
            User user  = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("user not found"));;
            return user;
        }
        return null;
    }
}
