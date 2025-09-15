package com.example.demo.service.trading;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;

public interface ITradingAccountService {
    TradingAccountResponse get();
    TradingAccountResponse create();
}
