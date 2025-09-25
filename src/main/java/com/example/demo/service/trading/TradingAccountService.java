package com.example.demo.service.trading;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.request.tradingAccount.TradingAccountRequest;
import com.example.demo.dto.response.tradingAccount.TradingAccountResponse;
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
    public TradingAccountResponse get(UUID accountId) {
        BankAccount bankAccount = bankAccountRepository.findBankAccountById(accountId);
        TradingAccount tradingAcocunt  = tradingAccountRepository
                .findTradingAccountByBankAccount(bankAccount)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return tradingAccountMapper.toTradingAccountResponse(tradingAcocunt);
    }

    @Override
    public TradingAccountResponse create(TradingAccountRequest request) {
        User user = userContextUtils.getAuthenticationUsername();

        log.info("Create TradingAccount request: {}", request.getBankAccountId());

        BankAccount bankAccount = bankAccountRepository.findBankAccountById(request.getBankAccountId());
        if(bankAccount == null) {
            throw new RuntimeException("Bank account not found");
        }
        log.info("Bank account number: " + bankAccount);
        // add new trading account
        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setBankAccount(bankAccount);
        tradingAccount.setUser(user);
        tradingAccount.setStatus(AccountStatus.ACTIVE);

        return tradingAccountMapper.toTradingAccountResponse(tradingAccountRepository.save(tradingAccount));
    }

}
