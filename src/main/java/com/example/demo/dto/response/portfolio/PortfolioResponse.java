package com.example.demo.dto.response.portfolio;

import com.example.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioResponse {
    UUID portfolioId;
    User user;
    String name;
}
