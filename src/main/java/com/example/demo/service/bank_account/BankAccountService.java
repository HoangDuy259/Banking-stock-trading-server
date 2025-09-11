package com.example.demo.service.bank_account;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
//import com.example.demo.mapper.BankAccountMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.BaseRepository;
import com.example.demo.service.BaseService;
import com.example.demo.utils.bank.BankAccountUtils;
import com.example.demo.utils.enums.AccountStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountService extends BaseService<BankAccount, UUID> implements IBankAccountService {
    BankAccountRepository bankAccountRepository;
    UserRepository userRepository;
    BankAccountUtils bankAccountUtils;
//    BankAccountMapper bankAccountMapper;

    @Override
    protected BaseRepository<BankAccount, UUID> getRepository() {
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
        account.setStatus(AccountStatus.ACTIVE);
        return save(account);
    }

    @Override
    public List<BankAccountResponse> getAccountsByUser(Long userId) {

        return bankAccountRepository.findAllByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

//    @Override
//    public BankAccountResponse getById(UUID id) {
//        BankAccount account = bankAccountRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Bank account không tồn tại"));
//        return toResponse(account);
//    }

//    @Override
//    public BankAccountResponse updateStatus(UUID id, AccountStatus status) {
//        BankAccountResponse account = getById(id);
//        account.setStatus(status);
//        return bankAccountRepository.save(toResponse((account)));
//    }

//    @Override
//    public void deleteAccount(UUID id) {
//        bankAccountRepository.deleteById(id);
//    }

    private BankAccountResponse toResponse(BankAccount account) {
        BankAccountResponse res = new BankAccountResponse();
        res.setId(account.getId());
        res.setAccountNumber(account.getAccountNumber());
        res.setBalance(account.getBalance());
        res.setStatus(account.getStatus());
        res.setUserId(account.getUser().getId());
        return res;
    }

}
