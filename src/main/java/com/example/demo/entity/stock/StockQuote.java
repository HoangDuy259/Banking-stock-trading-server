package com.example.demo.entity.stock;

import com.example.demo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stock_quotes")
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockQuote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quote_id", nullable = false, updatable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    Stock stock;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "volume", nullable = false)
    Long volume;

    @Column(nullable = false)
    String timestamp;
}
