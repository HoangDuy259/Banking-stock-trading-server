package com.example.demo.controller;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.service.BankAccountService;
import com.example.demo.utils.enums.BankAccountStatus;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    // tạo account mới cho 1 user
    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountRequest dto) {
        BankAccount account = bankAccountService.createBankAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // lấy tất cả account của 1 user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccount>> getAccountsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bankAccountService.getAccountsByUser(userId));
    }

    // lấy chi tiết 1 account
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bankAccountService.getById(id));
    }

    // cập nhật trạng thái account (VD: OPEN -> CLOSED)
    @PatchMapping("/{id}/status")
    public ResponseEntity<BankAccount> updateStatus(@PathVariable UUID id,
                                                    @RequestParam BankAccountStatus status) {
        return ResponseEntity.ok(bankAccountService.updateStatus(id, status));
    }

    // xoá account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}

