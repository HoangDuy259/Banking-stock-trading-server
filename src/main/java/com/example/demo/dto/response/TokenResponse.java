package com.example.demo.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class TokenResponse {
    private UUID id;
    private String email;
    private String phoneNum;
}