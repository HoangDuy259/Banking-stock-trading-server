package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;              // thời gian sống của access token
    private long refreshTokenExpiresIn;  // thời gian sống của refresh token

}
