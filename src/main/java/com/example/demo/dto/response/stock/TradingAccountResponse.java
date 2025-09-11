package com.example.demo.dto.response.stock;


import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.utils.enums.AccountStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccountResponse {
    User user;
    String accountNumber;
    BankAccount bankAccount;
    BigDecimal cashBalance;
    AccountStatus accountStatus;
}
