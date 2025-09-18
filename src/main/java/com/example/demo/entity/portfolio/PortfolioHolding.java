package com.example.demo.entity.portfolio;

import com.example.demo.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "portfolio_holdings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PortfolioHolding {
    @Id
    @Column(name = "holding_id ", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id ", nullable = false)
    Portfolio portfolio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    Stock stock;

    @Column(name="quantity", nullable = false)
    int quantity;

    @Column(name="avg_prices", nullable = false, precision = 19, scale = 2)
    BigDecimal avgPrices;
}
