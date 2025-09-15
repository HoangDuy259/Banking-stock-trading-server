package com.example.demo.dto.request.stock;

import com.example.demo.utils.enums.OrderTypes;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PorfolioCreateRequest {
    @NotNull(message = "Portfolio's name cannot be null")
    String portfolioName; // ten danh muc dau tu
}
