package com.example.demo.dto.request.bank;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankTransactionRequest {
    @NotNull(message = "Account ID is required")
    private UUID fromAccountId;

    @NotNull(message = "Account ID is required")
    private UUID toAccountId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Description is require")
    private String description;
}
