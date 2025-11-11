package com.example.demo.dto.response.bank;

import com.example.demo.utils.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BankTransactionResponse {
    UUID id;
//    UUID sourceAccountId;
//    UUID destinationAccountId;
    String sourceAccountNumber;
    String destinationAccountNumber;
    BigDecimal amount;
    TransactionStatus status;
    String description;
    LocalDateTime createdDate;
}
