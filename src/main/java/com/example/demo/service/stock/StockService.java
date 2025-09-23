package com.example.demo.service.stock;

import com.example.demo.dto.request.stock.StockRequest;
import com.example.demo.dto.response.stock.StockResponse;
import com.example.demo.entity.stock.Stock;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repository.StockRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockService implements IStockService {
    StockRepository stockRepository;
    StockMapper stockMapper;

    @Override
    public StockResponse create(StockRequest request) {
        Stock stock1 = stockRepository.findBySymbol(request.getSymbol()).orElse(null);
        if(stock1 != null){
            throw new RuntimeException("Stock symbol already exists");
        }
        Stock stock = stockMapper.toStock(request);
        log.info("Creating stock {}", request.getOrganName());
        return stockMapper.toStockResponse(stockRepository.save(stock));
    }

    @Override
    public List<StockResponse> getAllStock() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockResponse> stockResponses = new ArrayList<>();
        stocks.forEach(stock ->
                stockResponses.add(stockMapper.toStockResponse(stock)));
        return stockResponses;
    }
}
