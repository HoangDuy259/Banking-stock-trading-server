package com.example.demo.entity.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrderMatchingEvent {
    UUID buyOrderId;
    UUID sellOrderId;
    UUID stockId;
    BigDecimal price;
    int quantity;
    LocalDateTime createdAt;
}
