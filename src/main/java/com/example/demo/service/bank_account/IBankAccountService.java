package com.example.demo.service.bank_account;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.bank.BankAccount;

import java.util.List;
import java.util.UUID;

public interface IBankAccountService{
    BankAccount createBankAccount(String username);
    List<BankAccountResponse> getAccountsByUser(Long userId);
};
