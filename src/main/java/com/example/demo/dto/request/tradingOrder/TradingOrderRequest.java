package com.example.demo.dto.request.tradingOrder;

import com.example.demo.utils.enums.OrderTypes;
import com.example.demo.validator.ValidOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidOrder
public class TradingOrderRequest {
    @NotNull(message = "Trading account id cannot be null")
    UUID tradingAccountId;

    @NotNull(message = "Stock id cannot be null")
    UUID stockId;          // mã cổ phiếu

    @NotNull(message = "Order type is required")
    OrderTypes orderType;  // loại lệnh (BUY/SELL...)

    @NotNull(message = "Price can not be null")
    BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 1000000, message = "Quantity cannot exceed 1,000,000")
    Integer quantity;      // số lượng
}
