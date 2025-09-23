package com.example.demo.dto.response.stock;

import com.example.demo.entity.stock.Stock;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockQuoteResponse {
    UUID stockQuoteId;
    StockResponse stock;
    BigDecimal price;
    Long volume;
    BigDecimal ceilingPrice;
    BigDecimal floorPrice;
    String timestamp;
}
