package com.example.demo.dto.response.stock;


import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.dto.response.user.UserResponse;
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
    UserResponse user;
    BankAccountResponse bankAccount;
    BigDecimal cashBalance;
    AccountStatus status;
}
