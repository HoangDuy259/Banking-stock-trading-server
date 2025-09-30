package com.example.demo.entity.event;

import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrderEvent {
    UUID tradingOrderId;
    UUID tradingAccountId;
    UUID stockId;
    BigDecimal price;
    int quantity;
    OrderTypes orderType;
    OrderStatus status;
    LocalDateTime createdDate;
}
