package com.example.demo.controller;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.bank.BankAccount;
import com.example.demo.mapper.BankAccountMapper;
import com.example.demo.service.bank_account.IBankAccountService;
import com.example.demo.utils.auth.AuthUtils;
import com.example.demo.utils.enums.AccountStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.keycloak.authorization.client.util.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bank-accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountController {

    IBankAccountService bankAccountService;
    AuthUtils authUtils;
    BankAccountMapper bankAccountMapper;

    // tạo account mới cho 1 user
    @PostMapping
    public ResponseEntity<ApiResponse<BankAccountResponse>>createBankAccount() {
        String username = authUtils.getAuthenticationUsername();

        BankAccount account = bankAccountService.createBankAccount(username);

        ApiResponse<BankAccountResponse> res = ApiResponse.<BankAccountResponse>builder()
                .message("Tạo tài khoản thành công.")
                .result(bankAccountMapper.toDto(account))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // lấy tất cả account của 1 user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BankAccountResponse>>> getAccountsByUser(@PathVariable Long userId) {
        List<BankAccountResponse> accounts = bankAccountService.getAccountsByUser(userId);

        ApiResponse<List<BankAccountResponse>> res = ApiResponse.<List<BankAccountResponse>>builder()
                .message("Danh sách của người dùng: " + userId)
                .result(accounts)
                .build();
        return ResponseEntity.ok(res);
    }

    // khóa tài khoản
    @PatchMapping("/{accId}/lock")
    public ResponseEntity<ApiResponse<BankAccountResponse>> lockAccount(@PathVariable UUID accId){
        BankAccountResponse updatedAccount = bankAccountService.lockAccount(accId);
        ApiResponse<BankAccountResponse> res = ApiResponse.<BankAccountResponse>builder()
                .message("Khóa tài khoản thành công.")
                .result(updatedAccount)
                .build();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{accId}/unlock")
    public ResponseEntity<ApiResponse<BankAccountResponse>> unlockAccount(@PathVariable UUID accId){
        BankAccountResponse updatedAccount = bankAccountService.unlockAccount(accId);
        ApiResponse<BankAccountResponse> res = ApiResponse.<BankAccountResponse>builder()
                .message("Mở khóa tài khoản thành công.")
                .result(updatedAccount)
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/find")
    public ResponseEntity<ApiResponse<BankAccountResponse>> unlockAccount(@RequestParam String accNum) {
        BankAccountResponse foundedAccount = bankAccountService.findAccountByAccountNumber(accNum);
        ApiResponse<BankAccountResponse> res = ApiResponse.<BankAccountResponse>builder()
                .message("Tìm thấy tài khoản.")
                .result(foundedAccount)
                .build();
        return ResponseEntity.ok(res);
    }
}

