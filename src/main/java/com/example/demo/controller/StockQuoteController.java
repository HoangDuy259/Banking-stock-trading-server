package com.example.demo.controller;


import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.stock.StockQuoteResponse;
import com.example.demo.service.stock_quote.IStockQuoteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/stock-quotes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockQuoteController {
    IStockQuoteService stockQuoteService;

    @GetMapping("/{stockId}")
    public ResponseEntity<ApiResponse<List<StockQuoteResponse>>> getQuotesbyStock(@PathVariable UUID stockId) {
        try{
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", stockQuoteService.getQuotesbyStock(stockId)));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
