package com.example.demo.service.bank_transaction;

import com.example.demo.dto.response.bank.BankTransactionResponse;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.entity.bank.Transaction;
import com.example.demo.exception.AccountStatusException;
import com.example.demo.exception.ExistsException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.TransactionFailedException;
import com.example.demo.mapper.BankTransactionMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.BankTransactionRepository;
import com.example.demo.utils.enums.AccountStatus;
import com.example.demo.utils.enums.TransactionStatus;
import com.example.demo.utils.enums.TransactionTypes;
import com.example.demo.utils.transaction.TransactionUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.PessimisticLockException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankTransactionService implements IBankTransactionService {
    BankAccountRepository bankAccountRepository;
    BankTransactionRepository bankTransactionRepository;
    BankTransactionMapper bankTransactionMapper;
    TransactionTemplate transactionTemplate;
    TransactionUtils transactionUtils;

    @Override
    public void transfer(BankAccount fromAcc, BankAccount toAcc, BigDecimal amount){
        if (fromAcc.getId().equals(toAcc.getId())) {
            throw new IllegalArgumentException("Tài khoản gửi và nhận không thể trùng nhau");
        }

        if (!fromAcc.getStatus().equals(AccountStatus.ACTIVE) || !toAcc.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new AccountStatusException("Một trong hai tài khoản đang bị khóa hoặc không hợp lệ");
        }

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Số dư không đủ để thực hiện giao dịch");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));

        bankAccountRepository.save(fromAcc);
        bankAccountRepository.save(toAcc);

    }

    @Override
    public BankTransactionResponse transferWithRetry(UUID fromId, UUID toId, BigDecimal amount, String description){

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypes.TRANSFER);
        transaction.setStatus(TransactionStatus.START);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setDescription(description != null && !description.trim().isEmpty() ? description : "CHUYEN KHOAN");
        int attempt = 0;
        while(true){
            attempt++;
            try{
                transactionTemplate.execute(status -> {
                    // so sánh uuid cho việc sắp xếp để tránh deadlock
                    UUID first = fromId.compareTo(toId) < 0 ? fromId : toId;
                    UUID second = fromId.compareTo(toId) < 0 ? toId : fromId;
                    // tìm ra tài khoản từ first và second
                    BankAccount firstAcc = bankAccountRepository.findWithLockingById(first)
                            .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại."));
                    BankAccount secondAcc = bankAccountRepository.findWithLockingById(second)
                            .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại."));
                    // gán lại đúng vai trò của tài khoản
                    BankAccount fromAcc = fromId.equals(first) ? firstAcc : secondAcc;
                    BankAccount toAcc = toId.equals(first) ? firstAcc : secondAcc;

                    transaction.setSourceAccount(fromAcc);
                    transaction.setDestinationAccount(toAcc);
                    // triển khai transfer
                    transfer(fromAcc, toAcc, amount);
                    transaction.setStatus(TransactionStatus.SUCCESS);
                    bankTransactionRepository.save(transaction);
                    return null;
                });
                break;
            } catch(DataAccessException dae){
                if(transactionUtils.isRetryable(dae) && attempt < transactionUtils.getMAX_RETRY()){
                    transactionUtils.backoff(attempt);
                    continue;
                }else{
                    handleFailedTransaction(transaction, "Transfer failed after attempts: " + attempt + " - " + dae.getMessage());
                    throw new TransactionFailedException("Transfer failed after attempts: " + attempt, dae);
                }
            } catch(InsufficientBalanceException | AccountStatusException ex){
                handleFailedTransaction(transaction, ex.getMessage());
                throw ex;
            } catch (Exception ex){
                handleFailedTransaction(transaction, "Unexpected error");
                throw new TransactionFailedException("Unexpected error during transfer", ex);
            }
        }
        return bankTransactionMapper.toResponse(transaction);
    }

    @Override
    public List<BankTransactionResponse> findByAccountId(UUID accId){
        if (!bankAccountRepository.existsById(accId)) {
            throw new ExistsException("Tài khoản không tồn tại: " + accId);
        }
        List<Transaction> incomeTransaction = bankTransactionRepository.findAllByDestinationAccountId(accId);
        List<Transaction> outcomeTransaction = bankTransactionRepository.findAllBySourceAccountId(accId);
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(incomeTransaction);
        allTransactions.addAll(outcomeTransaction);
        allTransactions.sort(Comparator.comparing(Transaction::getCreatedDate).reversed());

        return bankTransactionMapper.toListResponse(allTransactions);
    }

    private void handleFailedTransaction(Transaction transaction, String message) {
        try {
            transactionTemplate.execute(status -> {
                Transaction failedTx = new Transaction();
                failedTx.setSourceAccount(transaction.getSourceAccount());
                failedTx.setDestinationAccount(transaction.getDestinationAccount());
                failedTx.setAmount(transaction.getAmount());
                failedTx.setTransactionType(transaction.getTransactionType());
                failedTx.setStatus(TransactionStatus.FAILED);
                failedTx.setDescription((transaction.getDescription() != null ? transaction.getDescription() + " | " : "")
                        + "FAILED: " + message);
                failedTx.setCreatedDate(transaction.getCreatedDate());
                bankTransactionRepository.save(failedTx);
                return null;
            });
        } catch (Exception e) {
            System.err.println("Error when saving failed transaction: " + e.getMessage());
        }
    }
}


