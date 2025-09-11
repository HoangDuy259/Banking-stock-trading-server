package com.example.demo.service.impl;

import com.example.demo.entity.bank.BankAccount;
import com.example.demo.repository.BankRepository;
import com.example.demo.repository.interfaces.IBaseRepository;
import com.example.demo.service.BaseService;

import java.util.UUID;

public class BankService extends BaseService<BankAccount, UUID> {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    protected IBaseRepository<BankAccount, UUID> getRepository() {
        return bankRepository;
    }

    // có thể override hoặc thêm method riêng
}
