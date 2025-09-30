package com.example.demo.service.trading_order_matching;

import com.example.demo.entity.event.TradingOrderStatusEvent;
import com.example.demo.utils.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderStatusProducer {
    KafkaTemplate<String, TradingOrderStatusEvent> kafkaTemplate;
    String topic = "order-status";

    // this function will be run once we processed a matching order
    // send order status to kafka function
    public void sendOrderStatus(TradingOrderStatusEvent event) {
        kafkaTemplate.send(topic, event.getTradingOrderId().toString(), event);
        log.info("Published order status: {}", event);
    }
}
