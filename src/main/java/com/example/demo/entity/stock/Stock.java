package com.example.demo.entity.stock;

import com.example.demo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock extends BaseEntity {
    @Id
    @Column(name = "stock_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "symbol", nullable = false, unique = true)
    String symbol;

    @Column(name = "company_name", nullable = true)
    String companyName;

    @Column(name = "current_price", nullable = false, precision = 19, scale = 2)
    BigDecimal currentPrice = BigDecimal.ZERO;

}
