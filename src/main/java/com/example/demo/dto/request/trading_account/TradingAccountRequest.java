package com.example.demo.dto.request.trading_account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccountRequest {
    String bankAccountId;
}
