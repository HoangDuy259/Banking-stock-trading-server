package com.example.demo.entity;

import com.example.demo.exception.OrderSide;
import com.example.demo.exception.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", columnDefinition = "uuid")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private TradingAccount account;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_side", nullable = false)
    private OrderSide orderSide;

    @Column(name = "price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "matched_quantity", nullable = false)
    private Long matchedQuantity = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
