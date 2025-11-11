package com.example.demo.repository;

import com.example.demo.entity.bank.BankAccount;
import feign.Param;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    //    hàm nào riêng của bank thì bỏ vào đây nè
    boolean existsByAccountNumber(String accountNumber);
    List<BankAccount> findAllByUser_Id(Long userId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    Optional<BankAccount> findWithLockingById(UUID id);
    BankAccount findBankAccountById(UUID id);
    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);
}
