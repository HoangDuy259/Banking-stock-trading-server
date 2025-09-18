package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.trading_account.TradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TradingAccountRepository extends JpaRepository<TradingAccount, UUID> {
    Optional<TradingAccount> findTradingAccountById(UUID id);
}
