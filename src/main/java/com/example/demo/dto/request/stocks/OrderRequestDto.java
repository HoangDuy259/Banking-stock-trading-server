package com.example.demo.dto.request.stocks;

import com.example.demo.exception.OrderSide;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderRequestDto {
    @NotNull(message = "Account ID is required")
    private UUID accountId;

    @NotBlank(message = "Stock ticker is required")
    private String stockTicker;

    @NotNull(message = "Order side is required")
    private OrderSide orderSide; // BUY or SELL

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
}
