package com.example.demo.dto.response.stock;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockResponse {
    UUID stockId;
    String symbol;
    String organName;
}
