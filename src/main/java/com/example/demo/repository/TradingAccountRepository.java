package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.stock.TradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradingAccountRepository extends JpaRepository<TradingAccount, Long> {
    TradingAccount findByUser(User user);
}
