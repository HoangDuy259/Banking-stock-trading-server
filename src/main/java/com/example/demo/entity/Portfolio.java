package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "portfolio", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "stock_id"})
})
@Data
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "portfolio_id", columnDefinition = "uuid", updatable = false)
    private UUID portfolioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private TradingAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "quantity", nullable = false)
    private Long quantity = 0L; // Số lượng cổ phiếu khả dụng (có thể bán)

}
