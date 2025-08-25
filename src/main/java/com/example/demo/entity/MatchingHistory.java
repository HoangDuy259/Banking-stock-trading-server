package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "matching_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "match_id", columnDefinition = "uuid", updatable = false)
    private UUID matchId;

    //gia khop lenh
    @Column(name = "match_price",nullable = false)
    private BigDecimal matchPrice;

    @Column(name = "match_quantity",nullable = false)
    private Long matchQuantity;

    @Column(name = "match_change", nullable = false, precision = 18, scale = 2)
    private BigDecimal matchChange;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buy_order_id", nullable = false)
    private Order buyOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sell_order_id", nullable = false)
    private Order sellOrder;
}
