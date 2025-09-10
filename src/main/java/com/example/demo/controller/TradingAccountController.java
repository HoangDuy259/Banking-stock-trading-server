package com.example.demo.controller;

import com.example.demo.dto.request.CreateTradingAccountDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trading")
@RequiredArgsConstructor
public class TradingAccountController {
    private final TradingAccountService tradingAccountService;

    @PostMapping
    public ResponseEntity<TradingAccount> addTradingAccount
            (@RequestBody @Valid CreateTradingAccountDto request) {
        TradingAccount tradingAccount = tradingAccountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tradingAccount);
    }
}
