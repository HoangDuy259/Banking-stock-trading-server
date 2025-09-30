package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.entity.event.TradingOrderMatchingEvent;
import com.example.demo.entity.event.TradingOrderStatusEvent;
import com.example.demo.service.trading_order.ITradingOrderService;
import com.example.demo.service.trading_order.TradingOrderProducer;
import com.example.demo.utils.enums.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderConsumer {
    MatchingEngine matchingEngine;
    ITradingOrderService tradingOrderService;
    OrderMatchingProducer tradeProducer;
    OrderStatusProducer orderStatusProducer;
    ObjectMapper objectMapper;
    private final TradingOrderProducer tradingOrderProducer;

    @KafkaListener(topics = "orders", groupId = "order-matcher")
    public void consumeOrder(String event) {
        try{
            log.info("Received order from Kafka: {}", event);
            // transfer event from string to dto class using object mapper
            TradingOrderEvent orderEvent = objectMapper.readValue(event, TradingOrderEvent.class);

            // using matching engine for process matching order and assign it to matching result object
            MatchingResult result = matchingEngine.process(orderEvent);

            // check if it has any trade element in list
            result.getTrades().forEach(trade -> {
                // publish each trade to topic trades of kafka
                tradeProducer.sendTrade(trade);
            });
            // check if it has any order status element in list
            result.getUpdateOrders().forEach(updateOrder -> {
                // publish all update orders to kafka
                orderStatusProducer.sendOrderStatus(updateOrder);
            });
        } catch (JsonMappingException e) {
            System.err.println("Error parsing message: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing message: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
