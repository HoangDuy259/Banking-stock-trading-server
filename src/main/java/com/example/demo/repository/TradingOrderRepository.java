package com.example.demo.repository;

import com.example.demo.entity.trading_account.TradingAccount;
import com.example.demo.entity.trading_transaction.TradingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TradingOrderRepository extends JpaRepository<TradingOrder, UUID> {
    List<TradingOrder> findAllByTradingAccount(TradingAccount tradingAccount);
}
