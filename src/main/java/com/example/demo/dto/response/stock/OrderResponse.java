package com.example.demo.dto.response.stock;


import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.stock.TradingAccount;
import com.example.demo.utils.enums.OrderTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    TradingAccount tradingAccount;
    Stock stock;
    OrderTypes orderType;
    Integer quantity;
}
