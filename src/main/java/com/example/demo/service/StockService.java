package com.example.demo.service;

import com.example.demo.dto.request.stocks.CreateStockRequest;
import com.example.demo.entity.Stock;



public interface StockService {
    Stock createStock(CreateStockRequest request);
}
