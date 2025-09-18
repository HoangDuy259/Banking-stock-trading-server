package com.example.demo.repository;

import com.example.demo.entity.stock.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockQuoteRepository  extends JpaRepository<StockQuote, UUID> {
}
