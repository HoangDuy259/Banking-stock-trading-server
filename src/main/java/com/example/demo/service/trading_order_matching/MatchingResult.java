package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.entity.event.TradingOrderMatchingEvent;
import com.example.demo.entity.event.TradingOrderStatusEvent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;



@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingResult {
    List<TradingOrderMatchingEvent> trades;
    List<TradingOrderStatusEvent> updateOrders;
}
