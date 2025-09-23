package com.example.demo.service.stock_quote;

import com.example.demo.dto.response.stock.StockQuoteResponse;

import java.util.List;
import java.util.UUID;

public interface IStockQuoteService {
    List<StockQuoteResponse> getQuotesbyStock(UUID stockId);
}
