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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
        account.setCreatedDate(LocalDateTime.now());
        return bankAccountRepository.save(account);
    }

    // gợi ý số đẹp
    @Override
    public List<String> suggestAccountNumbers(String prefix, int count) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "11"; // mặc định
        }
        if (!prefix.matches("\\d+")) {
            throw new IllegalArgumentException("Prefix must be numeric");
        }
        return bankAccountUtils.suggestBeautifulNumbers(prefix, count);
    }

    // tìm kiếm gần đúng
    @Override
    public List<String> searchSimilarAccountNumbers(String input) {
        if (input == null || input.isEmpty() || !input.matches("\\d+")) {
            return Collections.emptyList();
        }

        List<String> suggestions = new ArrayList<>();
        Set<String> unique = new HashSet<>();

        // Tạo 50 số gợi ý gần giống
        while (suggestions.size() < 10) {
            String candidate = bankAccountUtils.generateSimilarNumber(input);
            if (unique.add(candidate) && !bankAccountRepository.existsByAccountNumber(candidate)) {
                suggestions.add(candidate);
            }
        }

        return suggestions;
    }

    // kiểm tra và tạo custom
    @Override
    public BankAccount createCustomAccount(String username, String desiredNumber) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));

        // kiểm tra định dạng
        if (!bankAccountUtils.isValidFormat(desiredNumber)) {
            throw new IllegalArgumentException("Tài khoản phải có 11 chữ số");
        }

        // kiểm tra trùng
        if (bankAccountRepository.existsByAccountNumber(desiredNumber)) {
            throw new IllegalArgumentException("Tài khoản đã tồn tại");
        }

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setAccountNumber(desiredNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);

        return bankAccountRepository.save(account);
    }

    @Override
    public List<BankAccountResponse> getAccountsByUser(Long userId) {

        // Kiểm tra user tồn tại
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExistsException("Người dùng không tồn tại."));

        // Lấy danh sách tài khoản
        List<BankAccount> accounts = bankAccountRepository.findAllByUser_Id(userId);

        if (accounts.isEmpty()) {
            throw new ExistsException("Người dùng chưa có tài khoản nào.");
        }

        accounts.forEach(account ->
                log.info("Account ID: {}, CreatedDate: {}", account.getId(), account.getCreatedDate())
        );

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
        account.setStatus(AccountStatus.ACTIVE);
        bankAccountRepository.save(account);
        return bankAccountMapper.toDto(account);
    }

    @Override
    public BankAccountResponse findAccountByAccountNumber(String accNum) {
        BankAccount account = bankAccountRepository.findBankAccountByAccountNumber(accNum)
                .orElseThrow(() -> new ExistsException("Tài khoản không tồn tại."));
        return bankAccountMapper.toDto(account);
    }

}
