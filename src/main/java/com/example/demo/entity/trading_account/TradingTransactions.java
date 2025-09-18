package com.example.demo.entity.trading_account;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.stock.Stock;
import com.example.demo.utils.enums.TradingTransactionTypes;
import com.example.demo.utils.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "trading_transactions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingTransactions extends BaseEntity{
    @Id
    @Column(name = "trading_transaction_id", nullable = false, updatable = false)
    UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    TradingAccount tradingAccount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    Stock stock;

    @Column(name = "type", nullable = false, length = 10)
    TradingTransactionTypes type; // BUY hoặc SELL

    @Column(name = "quantity", nullable = true)
    Integer quantity;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    BigDecimal totalAmount;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    TransactionStatus status; // PENDING, COMPLETED, FAILED

}
