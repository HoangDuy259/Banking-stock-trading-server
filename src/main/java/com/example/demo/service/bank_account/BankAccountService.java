package com.example.demo.service.bank_account;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.exception.ExistsException;
import com.example.demo.mapper.BankAccountMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.bank.BankAccountUtils;
import com.example.demo.utils.enums.AccountStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountService implements IBankAccountService {
    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    BankAccountUtils bankAccountUtils;
    BankAccountMapper bankAccountMapper;

    @Override
    public BankAccount createBankAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accountNumber = bankAccountUtils.generateAccountNumber();

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        return bankAccountRepository.save(account);
    }

    @Override
    public List<BankAccountResponse> getAccountsByUser(Long userId) {

        List<BankAccount> accounts = bankAccountRepository.findAllByUser_Id(userId);
        log.info("getAccountsByUser: " + accounts);
        return bankAccountMapper.toDtoList(accounts);
    }

    @Override
    public BankAccountResponse lockAccount(UUID accId){
        BankAccount account = bankAccountRepository.findById(accId)
                .orElseThrow(() -> new ExistsException("Tài khoản không tồn tại."));
        account.setStatus(AccountStatus.INACTIVE);
        bankAccountRepository.save(account);
        return bankAccountMapper.toDto(account);
    }

    @Override
    public BankAccountResponse unlockAccount(UUID accId){
        BankAccount account = bankAccountRepository.findById(accId)
                .orElseThrow(() -> new ExistsException("Tài khoản không tồn tại."));
        account.setStatus(AccountStatus.INACTIVE);
        bankAccountRepository.save(account);
        return bankAccountMapper.toDto(account);
    }

    @Override
    public BankAccountResponse findAccountByAccountNumber(String accNum) {
        return null;
    }

}
