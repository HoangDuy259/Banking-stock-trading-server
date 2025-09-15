package com.example.demo.repository;

import com.example.demo.entity.bank.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends BaseRepository<BankAccount, UUID> {
    //    hàm nào riêng của bank thì bỏ vào đây nè
    boolean existsByAccountNumber(String accountNumber);
    List<BankAccount> findAllByUser_Id(Long userId);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
