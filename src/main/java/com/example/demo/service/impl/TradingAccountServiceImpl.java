package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateTradingAccountDto;
import com.example.demo.entity.TradingAccount;
import com.example.demo.entity.User;
import com.example.demo.exception.ExistsException;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TradingAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradingAccountServiceImpl implements TradingAccountService {
    private final TradingAccountRepository tradingAccountRepository;
    private final UserRepository userRepository;

    @Override
    public TradingAccount create(CreateTradingAccountDto request) {
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new ExistsException("User not found"));
//        Optional<TradingAccount> exTradingAccount = tradingAccountRepository.findByUser(user);
//        if (exTradingAccount.isPresent()) {
//            throw new ExistsException("Trading account already exists");
//        }
//        TradingAccount tradingAccount = new TradingAccount();
//        tradingAccount.setUser(user);
//        tradingAccount.setCashBalance(request.getCashBalance());
//        return tradingAccountRepository.save(tradingAccount);
        return null;
    }
}
