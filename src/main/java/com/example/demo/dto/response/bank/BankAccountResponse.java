package com.example.demo.dto.response.bank;

import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.utils.enums.AccountStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BankAccountResponse {
    UUID id;
    String accountNumber;
    BigDecimal balance;
    AccountStatus status;
    LocalDateTime createdDate;
    UserResponse user;
}
