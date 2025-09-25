package com.example.demo.dto.request.tradingAccount;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccountRequest {
    UUID bankAccountId;
}
