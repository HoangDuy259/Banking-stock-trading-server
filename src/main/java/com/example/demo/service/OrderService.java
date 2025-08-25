package com.example.demo.service;

import com.example.demo.dto.request.stocks.TradingOrderRequestDto;
import com.example.demo.dto.request.stocks.OrderRequestDto;
import com.example.demo.dto.response.OrderResponseDto;
import com.example.demo.entity.Order;

public interface OrderService {
    Order buyOrder(TradingOrderRequestDto request);
}
