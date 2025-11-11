package com.example.demo.mapper;

import com.example.demo.dto.request.bank.BankAccountRequest;
import com.example.demo.dto.response.bank.BankAccountResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.bank.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "createdDate", target = "createdDate")
    BankAccountResponse toDto(BankAccount bankAccount);

    @Mapping(source = "user", target = ".")
    UserResponse toUserResponse(User user);

    List<BankAccountResponse> toDtoList(List<BankAccount> bankAccounts);
}
