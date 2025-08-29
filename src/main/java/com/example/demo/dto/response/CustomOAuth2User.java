package com.example.demo.dto.response;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User oAuth2User;
    private final String accessToken;
    private final String refreshToken;

    public CustomOAuth2User(OAuth2User oAuth2User, String accessToken, String refreshToken) {
        this.oAuth2User = oAuth2User;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.put("accessToken", accessToken);
        attributes.put("refreshToken", refreshToken);
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
