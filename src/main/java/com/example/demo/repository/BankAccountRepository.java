package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    //    hàm nào riêng của bank thì bỏ vào đây nè
    boolean existsByAccountNumber(String accountNumber);
    List<BankAccount> findAllByUser_Id(Long userId);

    Optional<BankAccount> findByUser(User user);
}
