package com.example.demo.dto.response.tradingOrder;

import com.example.demo.dto.response.stock.StockResponse;
import com.example.demo.dto.response.tradingAccount.TradingAccountResponse;
import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradingOrderResponse {
    UUID tradingOrderId;
    TradingAccountResponse tradingAccount;
    StockResponse stock;
    BigDecimal price;
    OrderTypes orderType;
    OrderStatus orderStatus;
    Integer quantity;
}
