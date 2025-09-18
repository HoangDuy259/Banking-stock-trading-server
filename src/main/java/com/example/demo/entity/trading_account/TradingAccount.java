package com.example.demo.entity.trading_account;


import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.utils.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "trading_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingAccount extends BaseEntity {
    @Id
    @Column(name = "trading_account_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "bank_account_id", nullable = true)
    BankAccount bankAccount;

    @Column(name = "cash_balance", nullable = false, precision = 19, scale = 2)
    BigDecimal cashBalance = BigDecimal.ZERO;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    AccountStatus status;
}
