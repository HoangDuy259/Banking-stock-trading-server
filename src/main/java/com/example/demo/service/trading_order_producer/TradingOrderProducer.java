package com.example.demo.service.trading_order_producer;


import com.example.demo.entity.event.TradingOrderEvents;
import com.example.demo.entity.trading_transaction.TradingOrder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingOrderProducer {
    KafkaTemplate<String, TradingOrderEvents> kafkaTemplate;
    String TOPIC = "orders";

    public void sendOrder(TradingOrderEvents event) {
        kafkaTemplate.send(TOPIC, event.getTradingOrderId().toString(), event);
        System.out.println("✅ Order published to Kafka: " + event);
    }
}
