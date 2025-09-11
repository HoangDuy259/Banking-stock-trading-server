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
public class OrderCreateRequest {
    @NotNull(message = "Trading account number cannot be null")
    String tradingAccountNumber;

    @NotNull(message = "Stock symbol cannot be null")
    String stockSymbol;          // mã cổ phiếu

    @NotNull(message = "Order type is required")
    OrderTypes orderType;  // loại lệnh (BUY/SELL...)

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 1000000, message = "Quantity cannot exceed 1,000,000")
    Integer quantity;      // số lượng
}
