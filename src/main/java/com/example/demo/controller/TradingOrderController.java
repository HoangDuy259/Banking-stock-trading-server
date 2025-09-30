package com.example.demo.controller;


import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.tradingAccount.TradingAccountResponse;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.service.trading_order.ITradingOrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/trading-order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingOrderController {
    ITradingOrderService tradingOrderService;
    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse<TradingOrderResponse>> newOrder(@RequestBody @Valid TradingOrderRequest request){
        try{
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", tradingOrderService.placeOrder(request)));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TradingOrderResponse>>> getAllOrders(){
        try{
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", tradingOrderService.getAllOrders()));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    @GetMapping("/{tradingOrderId}")
    public ResponseEntity<ApiResponse<TradingOrderResponse>> getTradingAccount(@PathVariable("tradingOrderId") UUID tradingOrderId) {
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", tradingOrderService.getTradingOrder(tradingOrderId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
