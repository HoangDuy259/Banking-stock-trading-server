package com.example.demo.repository;

import com.example.demo.entity.bank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankTransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllBySourceAccountId(UUID accId);
    List<Transaction> findAllByDestinationAccountId(UUID accId);
}
