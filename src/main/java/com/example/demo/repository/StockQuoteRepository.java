package com.example.demo.repository;

import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.stock.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockQuoteRepository  extends JpaRepository<StockQuote, UUID> {
    // Tìm bản ghi mới nhất theo stock symbol
    Optional<StockQuote> findTopByStockOrderByTimestampDesc(Stock stock);

    //Find by stock object
    List<StockQuote> findStockQuoteByStock(Stock stock);
}
