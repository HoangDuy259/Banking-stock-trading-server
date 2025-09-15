package com.example.demo.mapper;

import com.example.demo.dto.request.auth.UserRegisterRequest;
//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.stock.TradingAccount;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TradingAccountMapper {

    TradingAccountResponse toTradingAccountResponse(TradingAccount tradingAccount);
}
