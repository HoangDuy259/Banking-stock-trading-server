package com.example.demo.utils.transaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.PessimisticLockException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionUtils {
    int MAX_RETRY = 5;
    Long BASE_BACKOFF_MS = 100L;

    // back off là 1 phần của retry với cơ chế 100ms * 2^n (n là min(attempt, 6) = 2^6)
    public void backoff(int attempt) {
        try {
            long sleep = BASE_BACKOFF_MS * (1L << Math.min(attempt, 6));
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // kiểm tra coi nó có bị lỗi tạm thời không
    public boolean isRetryable(DataAccessException dae) {
        // detect deadlock/lock exceptions from common Spring exceptions
        return (dae instanceof PessimisticLockingFailureException) ||
                (dae instanceof CannotAcquireLockException) ||
                (dae instanceof DeadlockLoserDataAccessException) ||
                (dae.getCause() != null && dae.getCause() instanceof PessimisticLockException);
    }
}
