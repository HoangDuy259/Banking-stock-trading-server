package com.example.demo.repository;

import com.example.demo.entity.bank.BankAccount;
import com.example.demo.repository.interfaces.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends IBaseRepository<BankAccount, UUID> {
    //    hàm nào riêng của bank thì bỏ vào đây nè
    boolean existsByAccountNumber(String accountNumber);
    List<BankAccount> findAllByUser_Id(Long userId);
}
