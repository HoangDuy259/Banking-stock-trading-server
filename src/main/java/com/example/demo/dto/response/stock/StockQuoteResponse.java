package com.example.demo.dto.response.stock;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockQuoteResponse {
    StockResponse stockResponse;
    BigDecimal price;
    Long volume;
    String timestamp;
}
