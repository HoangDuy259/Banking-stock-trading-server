package com.example.demo.entity.event;

import com.example.demo.entity.BaseEntity;
import com.example.demo.utils.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrderEvents {
    UUID tradingOrderId;
    UUID tradingAccountId;
    UUID StockId;
    BigDecimal price;
    int quantity;
    OrderStatus status;
    @CreatedDate
    LocalDateTime createdDate;
}
