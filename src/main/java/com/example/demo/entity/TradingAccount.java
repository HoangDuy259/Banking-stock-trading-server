package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "trading_account")
@Data
public class TradingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false, columnDefinition = "uuid", updatable = false)
    private UUID accountId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "cash_balance", precision = 18, scale = 2)
    private BigDecimal cashBalance = BigDecimal.ZERO;

}
