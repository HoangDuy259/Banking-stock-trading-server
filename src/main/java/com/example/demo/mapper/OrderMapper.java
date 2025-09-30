package com.example.demo.mapper;


import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.entity.trading_order.TradingOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    TradingOrderResponse toOrderResponse(TradingOrder tradingOrder);
}
