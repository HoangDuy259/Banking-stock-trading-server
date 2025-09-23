package com.example.demo.mapper;

import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.entity.trading_transaction.TradingOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradingOrderMapper {
    TradingOrderResponse toTradingOrderResponse(TradingOrder tradingOrder);
}
