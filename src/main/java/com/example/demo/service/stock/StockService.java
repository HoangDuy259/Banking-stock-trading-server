package com.example.demo.service.stock;

import com.example.demo.dto.request.stock.StockRequest;
import com.example.demo.dto.response.stock.StockResponse;
import com.example.demo.repository.StockRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockService implements IStockService {
    StockRepository stockRepository;

    @Override
    public StockResponse create(StockRequest request) {
        return null;
    }
}
