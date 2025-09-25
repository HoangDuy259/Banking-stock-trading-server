package com.example.demo.dto.request.stock;

import com.example.demo.dto.response.stock.StockResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockQuoteRequest {
    String symbol;
    BigDecimal price;
    long volume;
    String timestamp;
    BigDecimal ceilingPrice;
    BigDecimal floorPrice;
}
