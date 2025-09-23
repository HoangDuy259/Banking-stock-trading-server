package com.example.demo.validator;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.stock.StockQuoteResponse;
import com.example.demo.entity.stock.Stock;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.stock_quote.StockQuoteService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingOrderValidator implements ConstraintValidator<ValidOrder, TradingOrderRequest> {
    StockQuoteService stockQuoteService;
    StockRepository stockRepository;

    @Override
    public boolean isValid(TradingOrderRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request.getPrice() == null){
            return false;
        }
        Stock stock = stockRepository.findById(request.getStockId()).orElseThrow(
                () -> new IllegalArgumentException("Stock Not Found")
        );
        StockQuoteResponse stockQuoteResponse = stockQuoteService.getLastedQuote(stock);
        return request.getPrice().compareTo(stockQuoteResponse.getFloorPrice()) >= 0
                && request.getPrice().compareTo(stockQuoteResponse.getCeilingPrice()) <= 0;
    }
}
