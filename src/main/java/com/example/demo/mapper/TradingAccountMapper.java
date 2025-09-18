package com.example.demo.mapper;

//import com.example.demo.dto.request.stock.TradingAccountCreateRequest;
import com.example.demo.dto.response.stock.TradingAccountResponse;
        import com.example.demo.entity.trading_account.TradingAccount;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TradingAccountMapper {

    TradingAccountResponse toTradingAccountResponse(TradingAccount tradingAccount);
}
