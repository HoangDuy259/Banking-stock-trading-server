package com.example.demo.controller;

import com.example.demo.dto.request.bank.BankTransactionRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.bank.BankTransactionResponse;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.service.bank_transaction.IBankTransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bank-transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankTransactionController {
    IBankTransactionService bankTransactionService;
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<BankTransactionResponse>> transfer(@RequestBody @Valid BankTransactionRequest req){
        try{
            BankTransactionResponse transfer = bankTransactionService.transferWithRetry(req.getFromAccountId(), req.getToAccountId(), req.getAmount(), req.getDescription());
            ApiResponse<BankTransactionResponse> res = ApiResponse.<BankTransactionResponse>builder()
                    .message("Giao dịch thành công")
                    .result(transfer)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch(InsufficientBalanceException ex){
            ApiResponse<BankTransactionResponse> res = ApiResponse.<BankTransactionResponse>builder()
                    .message(ex.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

    @GetMapping("/{accId}")
    public ResponseEntity<ApiResponse<List<BankTransactionResponse>>> getTransactionsByAccId(@PathVariable UUID accId) {
        try {
            List<BankTransactionResponse> transactions = bankTransactionService.findByAccountId(accId);
            ApiResponse<List<BankTransactionResponse>> res = ApiResponse.<List<BankTransactionResponse>>builder()
                    .message("Lấy danh sách giao dịch thành công")
                    .result(transactions)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
