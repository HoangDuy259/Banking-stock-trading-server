package com.example.demo.service.trading;

import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.entity.stock.TradingAccount;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.UserContextUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingAccountService implements ITradingAccountService {
    TradingAccountRepository tradingAccountRepository;
    //get current user
    UserContextUtils userContextUtils;
    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    @Override
    public TradingAccountResponse get() {
        return null;
    }


    @Override
    public TradingAccountResponse create(TradingAccountCreateRequest request) {
        String username = userContextUtils.getAuthenticationUsername();
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(
                () -> new RuntimeException("Bank account number not found"));

        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setBankAccount(bankAccount);
        tradingAccount.setUser(user);

        return null;
    }

}
