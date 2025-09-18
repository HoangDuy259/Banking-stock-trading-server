package com.example.demo.service.stock_quote;

import com.example.demo.dto.request.stock.StockQuoteRequest;
import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.stock.StockQuote;
import com.example.demo.mapper.StockQuoteMapper;
import com.example.demo.repository.StockQuoteRepository;
import com.example.demo.repository.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockQuoteService {
    StockQuoteRepository stockQuoteRepository;
    StockRepository stockRepository;
    StockQuoteMapper stockQuoteMapper;
    ObjectMapper objectMapper;
    @KafkaListener(topics = "stock-quotes", groupId = "stock-consumer-group")
    public void getQuote(String message) {
        try {
            //get Stock by symbol in stock quote
            JsonNode node = objectMapper.readTree(message);
            String symbol = node.get("symbol").asText();
            System.out.println("Symbol = " + symbol);
            Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(
                    () -> new RuntimeException("Stock not found")
            );

            //Mapping message stock to stock quote request
            StockQuoteRequest stockQuoteRequest = objectMapper.readValue(message, StockQuoteRequest.class);
            //Mapping request to quote ignore column stock
            StockQuote quote = stockQuoteMapper.toQuote(stockQuoteRequest);
            quote.setStock(stock);

            System.out.println("Saved to DB: " + quote.getStock().getSymbol() + " - " + quote.getPrice());

            //Save quote to db and return quote response for clients
            stockQuoteMapper.toResponse(stockQuoteRepository.save(quote));
        } catch (Exception e) {
            System.err.println("Error parsing message: " + message);
            e.printStackTrace();
        }
    }
}
