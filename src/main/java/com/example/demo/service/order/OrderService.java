package com.example.demo.service.order;

import com.example.demo.dto.request.stock.OrderCreateRequest;
import com.example.demo.dto.response.stock.OrderResponse;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.TradingAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {
    OrderRepository orderRepository;
    TradingAccountRepository tradingAccountRepository;
    StockRepository stockRepository;
    @Override
    public OrderResponse buy(OrderCreateRequest request) {

        return null;
    }
}
