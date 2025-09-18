package com.example.demo.dto.request.portfolio;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioCreateRequest {
    @NotNull(message = "Portfolio's name can not be null")
    String portfolioName; // ten danh muc dau tu
}
