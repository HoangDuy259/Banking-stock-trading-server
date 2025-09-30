package com.example.demo.service.trading_order;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.utils.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface ITradingOrderService {
    TradingOrderResponse placeOrder(TradingOrderRequest request);
    List<TradingOrderResponse> getAllOrders();
    TradingOrderResponse getTradingOrder(UUID tradingOrderId);
}
