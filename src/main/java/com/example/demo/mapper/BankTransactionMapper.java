package com.example.demo.mapper;

import com.example.demo.dto.response.bank.BankTransactionResponse;
import com.example.demo.entity.bank.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankTransactionMapper {
    @Mapping(source = "sourceAccount.accountNumber", target = "sourceAccountNumber")
    @Mapping(source = "destinationAccount.accountNumber", target = "destinationAccountNumber")
    BankTransactionResponse toResponse(Transaction transaction);
    List<BankTransactionResponse> toListResponse(List<Transaction> transaction);
}
