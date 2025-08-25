package com.example.demo.dto.request.stocks;

import com.example.demo.exception.StockStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateStockRequest {
    private String stockTicker;
    private String stockCompanyName;
    private BigDecimal stockReferencePrice;
    private BigDecimal stockCeilingPrice;
    private BigDecimal stockFloorPrice;
    private StockStatus status;
}
