package com.example.demo.controller;

import com.example.demo.dto.request.stocks.TradingOrderRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/buy")
    public ResponseEntity<Order> buyOrder
            (@Valid @RequestBody TradingOrderRequestDto request) {
        Order order = orderService.buyOrder(request);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
