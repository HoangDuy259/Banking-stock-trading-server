package com.example.demo.entity.stock;

import com.example.demo.entity.BaseEntity;
import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "trading_orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {
    @Id
    @Column(name = "order_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_account_id", nullable = false)
    TradingAccount account;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "stock_id", nullable = false)
    Stock stock;

    @Column(name = "order_type", nullable = false, unique = true, length = 50)
    @Enumerated(EnumType.STRING)
    OrderTypes orderType;

    @Column(name = "quantity", nullable = true)
    Integer quantity;

    @Column(name = "order_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
