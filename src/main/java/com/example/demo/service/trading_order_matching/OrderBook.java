package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.utils.enums.OrderTypes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderBook {
    // Buy: get higest price first
    TreeMap<BigDecimal, Queue<TradingOrderEvent>> buyOrders =
            new TreeMap<>(Comparator.reverseOrder());

    // Sell: get lowest price first
    TreeMap<BigDecimal, Queue<TradingOrderEvent>> sellOrders =
            new TreeMap<>();

    // add new order to order book
    public synchronized void addOrder(TradingOrderEvent order) {
        if(order.getOrderType() == OrderTypes.BUY) {

            // check if the price existed or not,
            // if it does add another order to it, if not add a new list
            buyOrders
                    .computeIfAbsent(order.getPrice(), price -> new LinkedList<>())
                    .add(order);
        }
        else {

            // same to buyOrder above
            sellOrders
                    .computeIfAbsent(order.getPrice(), price -> new LinkedList<>())
                    .add(order);
        }
    }

    // Get order has higest price
    public synchronized Map.Entry<BigDecimal, Queue<TradingOrderEvent>> getBestBuy() {
        return buyOrders.isEmpty() ? null : buyOrders.firstEntry();
    }

    // Get order has lowest price
    public synchronized Map.Entry<BigDecimal, Queue<TradingOrderEvent>> getBestSell() {
        return sellOrders.isEmpty() ? null : sellOrders.firstEntry();
    }

}
