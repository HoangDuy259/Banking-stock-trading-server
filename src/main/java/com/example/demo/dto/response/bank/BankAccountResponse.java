package com.example.demo.dto.response.bank;

import com.example.demo.utils.enums.BankAccountStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccountResponse {
    UUID id;
    String accountNumber;
    BigDecimal balance;
    BankAccountStatus status;
    UUID userId;
}
