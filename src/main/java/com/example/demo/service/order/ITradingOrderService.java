package com.example.demo.service.order;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;

import java.util.List;
import java.util.UUID;

public interface ITradingOrderService {
    TradingOrderResponse placeOrder(TradingOrderRequest request);
    List<TradingOrderResponse> getAllOrders();
}
