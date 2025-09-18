package com.example.demo.service.trading;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.request.trading_account.TradingAccountRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.entity.trading_account.TradingAccount;
import com.example.demo.mapper.TradingAccountMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.utils.UserContextUtils;
import com.example.demo.utils.enums.AccountStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingAccountService implements ITradingAccountService {
    TradingAccountRepository tradingAccountRepository;
    //get current user
    UserContextUtils userContextUtils;
    BankAccountRepository bankAccountRepository;
    TradingAccountMapper tradingAccountMapper;
    @Override
    public TradingAccountResponse get(String accountId) {
        UUID id =  UUID.fromString(accountId);
        TradingAccount tradingAcocunt  = tradingAccountRepository
                .findTradingAccountById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return tradingAccountMapper.toTradingAccountResponse(tradingAcocunt);
    }

    @Override
    public TradingAccountResponse create(TradingAccountRequest request) {
        User user = userContextUtils.getAuthenticationUsername();

        BankAccount bankAccount = bankAccountRepository.find(user).orElseThrow(
                () -> new RuntimeException("Bank account number not found"));

        log.info("Bank account number: " + bankAccount);

        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setBankAccount(bankAccount);
        tradingAccount.setUser(user);
        tradingAccount.setStatus(AccountStatus.ACTIVE);
        return tradingAccountMapper.toTradingAccountResponse(tradingAccountRepository.save(tradingAccount));
    }

}
