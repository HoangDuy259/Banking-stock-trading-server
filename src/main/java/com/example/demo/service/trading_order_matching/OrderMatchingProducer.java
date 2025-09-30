package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderMatchingEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderMatchingProducer {
    KafkaTemplate<String, TradingOrderMatchingEvent> kafkaTemplate;
    String topic = "orders-matching";

    // this function will be run once we processed a matching order
    // send matching order to kafka function
    public void sendTrade(TradingOrderMatchingEvent trade) {
        kafkaTemplate.send(topic, trade.getBuyOrderId().toString(), trade);
        log.info("Published trade to Kafka: {}", trade);
    }
}
