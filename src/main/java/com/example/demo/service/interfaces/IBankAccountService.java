package com.example.demo.service.interfaces;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.utils.enums.BankAccountStatus;

import java.util.List;
import java.util.UUID;

public interface IBankAccountService extends IBaseService<BankAccount, UUID>{
    BankAccount createBankAccount(BankAccountRequest dto);
    List<BankAccount> getAccountsByUser(Long userId);
    BankAccount getById(UUID id);
    BankAccount updateStatus(UUID id, BankAccountStatus status);
    void deleteAccount(UUID id);
};
