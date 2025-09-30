package com.example.demo.service.trading_order;


import com.example.demo.entity.event.TradingOrderEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingOrderProducer {
    KafkaTemplate<String, TradingOrderEvent> kafkaTemplate;
    String topic = "orders";

    // after add a new trading order success this function will be run to publish that order to kafka for handle
    public void sendOrder(TradingOrderEvent event) {
        kafkaTemplate.send(topic, event.getTradingOrderId().toString(), event);
        System.out.println("Order published to Kafka: " + event);
    }
}
