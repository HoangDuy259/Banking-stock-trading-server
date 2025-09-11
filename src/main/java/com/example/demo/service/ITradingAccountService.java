package com.example.demo.service;

import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;

public interface ITradingAccountService {
    TradingAccountResponse get(String accountNumber);
    TradingAccountResponse update(TradingAccountCreateRequest request);
    TradingAccountResponse create(TradingAccountCreateRequest request);
}
