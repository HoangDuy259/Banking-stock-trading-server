package com.example.demo.controller;

import com.example.demo.dto.request.stocks.CreateStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> addStock(@RequestBody CreateStockRequest request) {
        Stock createdStock = stockService.createStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

}
