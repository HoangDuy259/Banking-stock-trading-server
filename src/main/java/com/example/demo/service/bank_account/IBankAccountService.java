package com.example.demo.service.bank_account;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.service.interfaces.IBaseService;
import com.example.demo.utils.enums.AccountStatus;

import java.util.List;
import java.util.UUID;

public interface IBankAccountService extends IBaseService<BankAccount, UUID> {
    BankAccount createBankAccount(BankAccountRequest dto);
    List<BankAccountResponse> getAccountsByUser(Long userId);
//    BankAccountResponse getById(UUID id);
//    BankAccountResponse updateStatus(UUID id, AccountStatus status);
//    void deleteAccount(UUID id);
};
