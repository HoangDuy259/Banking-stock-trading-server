package com.example.demo.dto.response.bank;

import com.example.demo.utils.enums.BankAccountStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankAccountResponse {
    UUID id;
    String accountNumber;
    BigDecimal balance;
    BankAccountStatus status;
    Long userId;
}
