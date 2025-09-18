package com.example.demo.dto.request.stock;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockRequest {
    @NotNull(message = "Stock symbol cannot be null")
    String symbol;
    @NotNull(message = "Organization name cannot be null")
    String organName;
}
