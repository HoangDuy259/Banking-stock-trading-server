package com.example.demo.service;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.interfaces.IBaseRepository;
import com.example.demo.service.interfaces.IBankAccountService;
import com.example.demo.utils.bank.BankAccountUtils;
import com.example.demo.utils.enums.BankAccountStatus;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BankAccountService extends BaseService<BankAccount, UUID> implements IBankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountUtils bankAccountUtils;

    @Override
    protected IBaseRepository<BankAccount, UUID> getRepository() {
        return bankAccountRepository;
    }

    @Override
    public BankAccount createBankAccount(BankAccountRequest dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accountNumber = bankAccountUtils.generateAccountNumber();

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(BankAccountStatus.ACTIVE);

        return save(account);
    }

    @Override
    public List<BankAccount> getAccountsByUser(Long userId) {
        return bankAccountRepository.findAllByUser_Id(userId);
    }

    @Override
    public BankAccount getById(UUID id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account không tồn tại"));
    }

    @Override
    public BankAccount updateStatus(UUID id, BankAccountStatus status) {
        BankAccount account = getById(id);
        account.setStatus(status);
        return bankAccountRepository.save(account);
    }

    @Override
    public void deleteAccount(UUID id) {
        bankAccountRepository.deleteById(id);
    }


}
