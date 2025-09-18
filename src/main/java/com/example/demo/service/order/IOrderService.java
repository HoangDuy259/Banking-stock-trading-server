package com.example.demo.service.order;

import com.example.demo.dto.request.stock.OrderCreateRequest;
import com.example.demo.dto.response.stock.OrderResponse;

public interface IOrderService {
    OrderResponse buy(OrderCreateRequest request);
}
