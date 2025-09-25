package com.example.demo.service.stock;


import com.example.demo.dto.request.stock.StockRequest;
import com.example.demo.dto.response.stock.StockResponse;

import java.util.List;

public interface IStockService {
    StockResponse create(StockRequest request);
    List<StockResponse> getAllStock();
}
