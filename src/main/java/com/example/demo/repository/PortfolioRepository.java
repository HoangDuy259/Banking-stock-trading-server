package com.example.demo.repository;

import com.example.demo.entity.Portfolio;
import com.example.demo.entity.Stock;
import com.example.demo.entity.TradingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
    Optional<Portfolio> findByAccountAndStock(TradingAccount account, Stock stock);
}
