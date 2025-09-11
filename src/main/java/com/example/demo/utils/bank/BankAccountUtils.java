package com.example.demo.utils.bank;

import com.example.demo.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@AllArgsConstructor
@Component
public class BankAccountUtils {

    private static final SecureRandom random = new SecureRandom();

    private final BankAccountRepository bankAccountRepository;

    public String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf((long) (Math.random() * 1_000_000_00000L));
            accountNumber = String.format("%011d", Long.parseLong(accountNumber));
        } while (bankAccountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}

