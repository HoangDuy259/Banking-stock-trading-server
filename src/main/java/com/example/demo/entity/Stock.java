package com.example.demo.entity;


import com.example.demo.exception.StockStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stock")
@Data
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_id", nullable = false, columnDefinition = "uuid", updatable = false)
    private UUID stockId;

    @Column(name = "stock_ticker", nullable = false, unique = true, length = 10) // Dùng ticker (VNM, FPT) làm mã định danh
    private String stockTicker;

    @Column(name = "stock_company_name", nullable = false)
    private String stockCompanyName;

    @Column(name = "stock_reference_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal stockReferencePrice;

    @Column(name = "stock_ceiling_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal stockCeilingPrice;

    @Column(name = "stock_floor_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal stockFloorPrice;

    @Enumerated(EnumType.STRING) // Dùng Enum để code rõ ràng hơn
    @Column(name = "status", nullable = false)
    private StockStatus status = StockStatus.OPEN;
}

