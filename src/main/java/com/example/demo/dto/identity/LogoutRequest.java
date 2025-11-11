package com.example.demo.dto.identity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutRequest {
    private String client_id;
    private String client_secret;
    private String refresh_token;
}
