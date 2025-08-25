package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTradingAccountDto {
    @NotNull(message = "Khong duoc bo trong userId")
    private UUID userId;

    @NotNull(message = "Khong duoc bo trong cash")
    private BigDecimal cashBalance;
}
