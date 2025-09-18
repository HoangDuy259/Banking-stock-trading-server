package com.example.demo.mapper;

import com.example.demo.dto.request.stock.StockQuoteRequest;
import com.example.demo.dto.response.stock.StockQuoteResponse;
import com.example.demo.entity.stock.StockQuote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockQuoteMapper {
    StockQuoteResponse toResponse(StockQuote stockQuote);

    @Mapping(target = "stock", ignore = true)
    StockQuote toQuote(StockQuoteRequest stockQuoteRequest);
}
