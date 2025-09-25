package com.example.demo.mapper;

import com.example.demo.dto.request.stock.StockRequest;
import com.example.demo.dto.response.stock.StockResponse;
import com.example.demo.entity.stock.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    Stock toStock (StockRequest stockRequest);
    StockResponse toStockResponse (Stock stock);
}
