package com.example.demo.service;

import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingAccountService implements ITradingAccountService{
    @Override
    public TradingAccountResponse get(String accountNumber) {
        return null;
    }

    @Override
    public TradingAccountResponse update(TradingAccountCreateRequest request) {
        return null;
    }

    @Override
    public TradingAccountResponse create(TradingAccountCreateRequest request) {
        return null;
    }
}
