package com.example.demo.mapper;

import com.example.demo.dto.response.bank.BankTransactionResponse;
import com.example.demo.entity.bank.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankTransactionMapper {
    @Mapping(source = "sourceAccount.id", target = "sourceAccountId")
    @Mapping(source = "destinationAccount.id", target = "destinationAccountId")
    BankTransactionResponse toResponse(Transaction transaction);
    List<BankTransactionResponse> toListResponse(List<Transaction> transaction);
}
