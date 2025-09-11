package com.example.demo.entity.bank;

import com.example.demo.entity.BaseEntity;
import com.example.demo.utils.enums.TransactionStatus;
import com.example.demo.utils.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    @Column(name = "transaction_id", nullable = false, length = 50)
    UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sourceAccount_id", nullable = false)
    BankAccount sourceAccount;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destinationAccount_id", nullable = false)
    BankAccount destinationAccount;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    BigDecimal amount;

    // Loại giao dịch: TRANSFER, DEPOSIT, WITHDRAW
    @Column(name = "transaction_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    TransactionTypes transactionType;

    // Trạng thái: SUCCESS, FAILED, PENDING
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    TransactionStatus status;
}

