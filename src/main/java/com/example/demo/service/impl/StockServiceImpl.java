package com.example.demo.service.impl;

import com.example.demo.dto.request.stocks.CreateStockRequest;
import com.example.demo.entity.Stock;
import com.example.demo.exception.ExistsException;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;


    @Override
    public Stock createStock(CreateStockRequest request) {
        Optional<Stock> exStock = stockRepository.findByStockTicker(request.getStockTicker());
        if (exStock.isPresent()) {
        throw new ExistsException("TICKER ALREADY EXISTS");
        }
        Stock stock = new Stock();
        stock.setStockTicker(request.getStockTicker());
        stock.setStockCompanyName(request.getStockCompanyName());
        stock.setStockReferencePrice(request.getStockReferencePrice());
        stock.setStockCeilingPrice(request.getStockCeilingPrice());
        stock.setStockFloorPrice(request.getStockFloorPrice());
        stock.setStatus(request.getStatus());
        return stockRepository.save(stock);
    }


}
