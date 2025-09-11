package com.example.demo.dto.response.stock;

import com.example.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioResponse {
    User user;
    String portfolioName;
}
