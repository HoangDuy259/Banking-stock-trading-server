package com.example.demo.repository;

import com.example.demo.entity.bank.BankAccount;
import com.example.demo.repository.interfaces.IBaseRepository;

import java.util.UUID;

public interface BankRepository extends IBaseRepository<BankAccount, UUID> {
//    hàm nào riêng của bank thì bỏ vào đây nè
}
