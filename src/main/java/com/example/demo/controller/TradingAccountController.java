package com.example.demo.controller;


import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import com.example.demo.service.trading.TradingAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/trading-account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingAccountController {
    TradingAccountService tradingAccountService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TradingAccountResponse>> create(@Valid @RequestBody TradingAccountCreateRequest tradingAccountCreateRequest) {
        try {
            return ResponseEntity.status(CREATED).body(
                    new ApiResponse<>("Thành công", tradingAccountService.create(tradingAccountCreateRequest)));
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
