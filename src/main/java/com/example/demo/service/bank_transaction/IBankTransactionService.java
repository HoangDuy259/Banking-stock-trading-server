package com.example.demo.service.bank_transaction;

import com.example.demo.dto.response.bank.BankTransactionResponse;
import com.example.demo.entity.bank.BankAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IBankTransactionService {
    void transferWithRetry(UUID fromId, UUID toId, BigDecimal amount);
    void transfer(BankAccount fromId, BankAccount toId, BigDecimal amount);
    List<BankTransactionResponse> findByAccountId(UUID accId);

}
