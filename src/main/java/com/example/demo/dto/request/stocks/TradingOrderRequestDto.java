package com.example.demo.dto.request.stocks;

import com.example.demo.exception.OrderSide;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TradingOrderRequestDto {
    @NotNull(message = "Khong duoc bo trong account")
    private UUID accountId;

    @NotNull(message = "Khong duoc bo trong stock")
    private UUID stockId;

    @NotNull(message = "Khong duoc bo trong price")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull(message = "Khong duoc bo trong quantity")
    @Min(1)
    private Long quantity;

    @NotNull(message = "Khong duoc bo trong mua ban")
    private OrderSide orderSide;

}
