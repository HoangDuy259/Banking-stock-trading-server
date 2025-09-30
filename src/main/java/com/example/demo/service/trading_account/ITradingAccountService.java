package com.example.demo.service.trading_account;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.request.tradingAccount.TradingAccountRequest;
import com.example.demo.dto.response.tradingAccount.TradingAccountResponse;

import java.util.UUID;

public interface ITradingAccountService {
    TradingAccountResponse get(UUID accountId);
    TradingAccountResponse create(TradingAccountRequest request);
}
