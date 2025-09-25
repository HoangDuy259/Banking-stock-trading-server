package com.example.demo.service.trading_order;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;

import java.util.List;

public interface ITradingOrderService {
    TradingOrderResponse placeOrder(TradingOrderRequest request);
    List<TradingOrderResponse> getAllOrders();
}
