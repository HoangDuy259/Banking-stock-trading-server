package com.example.demo.service.trading;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.entity.stock.TradingAccount;
import com.example.demo.mapper.TradingAccountMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.UserContextUtils;
import com.example.demo.utils.enums.AccountStatus;
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

    TradingAccountMapper tradingAccountMapper;
    @Override
    public TradingAccountResponse get() {
        String username = userContextUtils.getAuthenticationUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));
        TradingAccount tradingAccount = tradingAccountRepository.findByUser(user);
        if(tradingAccount == null) {
            throw new RuntimeException("Trading account not found");
        }
        return tradingAccountMapper.toTradingAccountResponse(tradingAccount);
    }


    @Override
    public TradingAccountResponse create() {
        String username = userContextUtils.getAuthenticationUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));

        log.info("Trading account created:", tradingAccountRepository.findByUser(user));

        if(tradingAccountRepository.findByUser(user) != null) {
            throw new RuntimeException("Trading account already exists");
        }


        BankAccount bankAccount = bankAccountRepository.findByUser(user).orElseThrow(
                () -> new RuntimeException("Bank account number not found"));

        log.info("Bank account number: " + bankAccount);

        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setBankAccount(bankAccount);
        tradingAccount.setUser(user);
        tradingAccount.setStatus(AccountStatus.ACTIVE);
        return tradingAccountMapper.toTradingAccountResponse(tradingAccountRepository.save(tradingAccount));
    }

}
