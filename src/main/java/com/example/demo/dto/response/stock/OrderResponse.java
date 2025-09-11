package com.example.demo.dto.response.stock;


import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.stock.TradingAccount;
import com.example.demo.utils.enums.OrderTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    TradingAccount tradingAccount;
    Stock stock;
    OrderTypes orderType;
    Integer quantity;
}
