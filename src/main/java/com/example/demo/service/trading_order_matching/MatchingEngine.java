package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.entity.event.TradingOrderMatchingEvent;
import com.example.demo.entity.event.TradingOrderStatusEvent;
import com.example.demo.entity.trading_order.TradingOrder;
import com.example.demo.utils.enums.OrderStatus;
import com.example.demo.utils.enums.OrderTypes;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchingEngine {
    OrderBook orderBook = new OrderBook();

    // proccess matchbuy and matchsell function
    public synchronized MatchingResult process(TradingOrderEvent event) {
        //check if order events is buy order or sell order
        if (event.getOrderType() == OrderTypes.BUY) {
            return matchBuy(event);
        } else {
            return matchSell(event);
        }
    }

    private MatchingResult matchBuy(TradingOrderEvent buyOrder) {
        // list trades contain all trades after matching success
        List<TradingOrderMatchingEvent> trades = new ArrayList<>();
        // list order statuses for updating order after process finished
        List<TradingOrderStatusEvent> orderStatuses = new ArrayList<>();

        // using while to vailate quantity and get best sell order until it empty
        while (buyOrder.getQuantity() > 0 && orderBook.getBestSell() != null) {
            // get best sell order to compare
            var bestSell = orderBook.getBestSell();
            // declare best sell price and set it to best sell order's price before
            BigDecimal bestSellPrice = bestSell.getKey();

            //check if buy order has higher price than lowest sell price from best sell price object
            if (buyOrder.getPrice().compareTo(bestSellPrice) >= 0) {
                // get first order in list of the orders has the same sell price
                TradingOrderEvent sellOrder = bestSell.getValue().peek();
                // get quantity can matching between buy and sell orders
                int qty = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

                TradingOrderMatchingEvent trade = TradingOrderMatchingEvent.builder()
                        .buyOrderId(buyOrder.getTradingOrderId())
                        .sellOrderId(sellOrder.getTradingOrderId())
                        .stockId(buyOrder.getStockId())
                        .quantity(qty)
                        .price(bestSellPrice)
                        .createdAt(LocalDateTime.now())
                        .build();

                trades.add(trade);
                log.info("Trade executed: {}", trade);


                // update quantity after match
                buyOrder.setQuantity(buyOrder.getQuantity() - qty);
                sellOrder.setQuantity(sellOrder.getQuantity() - qty);

                //update status for order event before add to list
                updateStatus(buyOrder);
                updateStatus(sellOrder);

                // add new status event to publish to kafka
                // buy order status
                TradingOrderStatusEvent buyStatusEvent = new TradingOrderStatusEvent().builder()
                        .tradingOrderId(buyOrder.getTradingOrderId())
                        .StockId(buyOrder.getStockId())
                        .quantity(buyOrder.getQuantity() + qty)
                        .filledQuantity(qty)
                        .status(buyOrder.getStatus())
                        .createdDate(LocalDateTime.now())
                        .build();

                // sell order status
                TradingOrderStatusEvent sellStatusEvent = new TradingOrderStatusEvent().builder()
                        .tradingOrderId(sellOrder.getTradingOrderId())
                        .StockId(sellOrder.getStockId())
                        .quantity(sellOrder.getQuantity() + qty)
                        .filledQuantity(qty)
                        .status(sellOrder.getStatus())
                        .createdDate(LocalDateTime.now())
                        .build();


                // add to list order status
                orderStatuses.add(buyStatusEvent);
                orderStatuses.add(sellStatusEvent);
                // check if quantity is 0 then get first order value out of list queue and delete it
                if (sellOrder.getQuantity() == 0) bestSell.getValue().poll();
                //check if order has the same price of bestSell is exist, if not delete a price key out of TreeMap
                if (bestSell.getValue().isEmpty()) orderBook.getSellOrders().remove(bestSellPrice);


            } else break;
        }
        //check if quantity of buy order > 0, so continue add it to orderbook and match one more time, if not return
        if (buyOrder.getQuantity() > 0) orderBook.addOrder(buyOrder);
        return new MatchingResult(trades, orderStatuses);
    }

    //Same to match buy function
    private MatchingResult  matchSell(TradingOrderEvent sellOrder) {
        // list trades contain all trades after matching success
        List<TradingOrderMatchingEvent> trades = new ArrayList<>();
        // list order statuses for updating order after process finished
        List<TradingOrderStatusEvent> orderStatuses = new ArrayList<>();

        // using while vailate quantity and get best buy order until it null
        while (sellOrder.getQuantity() > 0 && orderBook.getBestBuy() != null) {
            // get first order in list of the orders has the same buy price
            var bestBuy = orderBook.getBestBuy();
            // get price of best buy order object for comparison
            BigDecimal bestBuyPrice = bestBuy.getKey();

            // compare if sell order has a higher price than the price we get from bestBuyPrice above
            if (sellOrder.getPrice().compareTo(bestBuyPrice) <= 0) {
                //declare a event ofject buy to the first element order of best buy queue but dont delete it from queue
                TradingOrderEvent buyOrder = bestBuy.getValue().peek();
                // get quantity can match between buy and sell orders
                int qty = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());

                // declare an order matching object and assign necessary value to it
                TradingOrderMatchingEvent trade = TradingOrderMatchingEvent.builder()
                        .buyOrderId(buyOrder.getTradingOrderId())
                        .sellOrderId(sellOrder.getTradingOrderId())
                        .stockId(sellOrder.getStockId())
                        .quantity(qty)
                        .price(bestBuyPrice)
                        .createdAt(LocalDateTime.now())
                        .build();

                trades.add(trade);
                log.info("Trade executed: {}", trade);

                //update quantity of sellOrders and buyOrders after proccessed
                sellOrder.setQuantity(sellOrder.getQuantity() - qty);
                buyOrder.setQuantity(buyOrder.getQuantity() - qty);

                //update status for order event before add to list
                updateStatus(sellOrder);
                updateStatus(buyOrder);

                // add new status event to publish to kafka
                // buy order status
                TradingOrderStatusEvent buyStatusEvent = new TradingOrderStatusEvent().builder()
                        .tradingOrderId(buyOrder.getTradingOrderId())
                        .StockId(buyOrder.getStockId())
                        .quantity(buyOrder.getQuantity() + qty)
                        .filledQuantity(qty)
                        .status(buyOrder.getStatus())
                        .createdDate(LocalDateTime.now())
                        .build();

                // sell order status
                TradingOrderStatusEvent sellStatusEvent = new TradingOrderStatusEvent().builder()
                        .tradingOrderId(sellOrder.getTradingOrderId())
                        .StockId(sellOrder.getStockId())
                        .quantity(sellOrder.getQuantity() + qty)
                        .filledQuantity(qty)
                        .status(sellOrder.getStatus())
                        .createdDate(LocalDateTime.now())
                        .build();


                // add to list order status
                orderStatuses.add(buyStatusEvent);
                orderStatuses.add(sellStatusEvent);

                // check if quantity is 0 then get first order value out of list queue and delete it
                if (buyOrder.getQuantity() == 0) bestBuy.getValue().poll();
                //check if order has the same price of bestSell exist, if not delete a price key out of TreeMap
                if (bestBuy.getValue().isEmpty()) orderBook.getBuyOrders().remove(bestBuyPrice);
            } else break;
        }

        if (sellOrder.getQuantity() > 0) {
            orderBook.addOrder(sellOrder);
        }

        return new MatchingResult(trades, orderStatuses);
    }

    public void updateStatus(TradingOrderEvent event) {
        if(event.getQuantity() > 0) {
            event.setStatus(OrderStatus.PARTIAL_FILLED);
        } else
            event.setStatus(OrderStatus.FILLED);
    }

}
