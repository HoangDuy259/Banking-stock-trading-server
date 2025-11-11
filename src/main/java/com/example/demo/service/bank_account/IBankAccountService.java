package com.example.demo.service.bank_account;

import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.bank.BankAccount;

import java.util.List;
import java.util.UUID;

public interface IBankAccountService{
    BankAccount createBankAccount(String username);
    List<BankAccountResponse> getAccountsByUser(Long userId);
    BankAccountResponse lockAccount(UUID accId);
    BankAccountResponse unlockAccount(UUID accId);
    BankAccountResponse findAccountByAccountNumber(String accNum);
    List<String> suggestAccountNumbers(String prefix, int count);
    List<String> searchSimilarAccountNumbers(String input);
    BankAccount createCustomAccount(String username, String desiredNumber);
};
