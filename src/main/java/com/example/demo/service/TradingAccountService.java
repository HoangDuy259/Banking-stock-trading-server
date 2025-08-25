package com.example.demo.service;

import com.example.demo.dto.request.CreateTradingAccountDto;
import com.example.demo.entity.TradingAccount;

public interface TradingAccountService {
    TradingAccount create(CreateTradingAccountDto request);
}
