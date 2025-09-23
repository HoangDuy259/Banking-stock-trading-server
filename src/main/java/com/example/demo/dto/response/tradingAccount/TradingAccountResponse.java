package com.example.demo.dto.response.tradingAccount;


import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.utils.enums.AccountStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccountResponse {
    UUID tradingAccountId;
    BankAccountResponse bankAccount;
    BigDecimal cashBalance;
    AccountStatus status;
}
