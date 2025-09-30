package com.example.demo.entity.event;

import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrderStatusEvent {
    UUID tradingOrderId;
    UUID StockId;
    int quantity;
    int filledQuantity;
    OrderStatus status;
    LocalDateTime createdDate;
}
