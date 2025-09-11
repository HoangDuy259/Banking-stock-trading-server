package com.example.demo.mapper;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.entity.bank.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount toBankAccount(BankAccountRequest request);

    BankAccountResponse toBankAccountResponse(BankAccount user);
}
