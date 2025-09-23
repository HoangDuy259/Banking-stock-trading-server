package com.example.demo.entity.stock;

import com.example.demo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
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
    UUID stockId;

    @Column(name = "symbol", nullable = false, unique = true)
    String symbol;

    @Column(name = "organ_name", nullable = false)
    String organName;

}
