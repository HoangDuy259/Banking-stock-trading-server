package com.example.demo.entity.trading_transaction;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.trading_account.TradingAccount;
import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "trading_orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrder extends BaseEntity {
    @Id
    @Column(name = "trading_order_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID tradingOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trading_account_id", nullable = false)
    TradingAccount tradingAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    Stock stock;

    @Column(name = "order_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    OrderTypes orderType;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    BigDecimal price;

    @Column(name = "order_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
}
