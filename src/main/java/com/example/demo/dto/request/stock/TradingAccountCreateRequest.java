package com.example.demo.dto.request.stock;

import com.example.demo.utils.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccountCreateRequest {
    @NotNull(message = "User name can not be null")
    String username;

    @NotBlank
    String accountNumber;

    @NotNull
    BigDecimal cashBalance;
}
