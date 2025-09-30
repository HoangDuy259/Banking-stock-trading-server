package com.example.demo.service.trading_order;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.stock.StockQuoteResponse;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.entity.event.TradingOrderEvent;
import com.example.demo.entity.event.TradingOrderStatusEvent;
import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.trading_account.TradingAccount;
import com.example.demo.entity.trading_order.TradingOrder;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.TradingOrderMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.repository.TradingOrderRepository;
import com.example.demo.service.stock_quote.StockQuoteService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradingOrderService implements ITradingOrderService {
    OrderRepository orderRepository;
    TradingAccountRepository tradingAccountRepository;
    TradingOrderRepository tradingOrderRepository;
    StockRepository stockRepository;
    StockQuoteService stockQuoteService;
    OrderMapper orderMapper;
    TradingOrderMapper tradingOrderMapper;
    TradingOrderProducer tradingOrderProducer;
    ObjectMapper objectMapper;

    @Override
    public TradingOrderResponse placeOrder(TradingOrderRequest request) {

        log.info("Create Order", request);

        //check if trading account existed
        TradingAccount tradingAccount = tradingAccountRepository.findById(request.getTradingAccountId()).orElseThrow(
                () -> new IllegalArgumentException("Trading Account Not Found")
        );

        //check if stock existed
        Stock stock = stockRepository.findById(request.getStockId()).orElseThrow(
                () -> new IllegalArgumentException("Stock Not Found")
        );

        //validate quantity
        if(request.getQuantity() == null || request.getQuantity() < 1) {
            throw new IllegalArgumentException("Invalid Quantity");
        }

        if (request.getQuantity() > 1_000_000) {
            throw new IllegalArgumentException("Quantity cannot exceed 1,000,000");
        }

        //validate price if it between floor price and celling price
        StockQuoteResponse latestQuote = stockQuoteService.getLastedQuote(stock);
        BigDecimal price = request.getPrice();
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (price.compareTo(latestQuote.getFloorPrice()) < 0 ||
                price.compareTo(latestQuote.getCeilingPrice()) > 0) {
            throw new IllegalArgumentException(
                    String.format("Price %s is not valid. Must be between %s and %s",
                            price, latestQuote.getFloorPrice(), latestQuote.getCeilingPrice())
            );
        }

        // Validate only for buy order
        // check if trading account balance valid for an order
        // skip transaction fee of stock exchange

//        BigDecimal requireAmmount = price.multiply(
//                BigDecimal.valueOf(request.getQuantity()));
//
//
//        BigDecimal balance = tradingAccount.getCashBalance();
//        if(balance.compareTo(requireAmmount) < 0) {
//            throw new IllegalArgumentException("Your balance is not enough to place this order");
//        }


        // validate only for sell order
        // check if trading account has valid quantity of the stock for sell


        //create trading orders after validate
        TradingOrder tradingOrder = new TradingOrder();
        tradingOrder.setStock(stock);
        tradingOrder.setTradingAccount(tradingAccount);
        tradingOrder.setQuantity(request.getQuantity());
        tradingOrder.setPrice(price);
        tradingOrder.setOrderType(request.getOrderType());
        tradingOrder.setOrderStatus(OrderStatus.PENDING);
        // Save order to db
        TradingOrder savedTradingOrder = orderRepository.save(tradingOrder);


        TradingOrderEvent tradingOrderEvents = new TradingOrderEvent().builder()
                .tradingOrderId(savedTradingOrder.getTradingOrderId())
                .tradingAccountId(savedTradingOrder.getTradingAccount().getTradingAccountId())
                .stockId(savedTradingOrder.getStock().getStockId())
                .price(savedTradingOrder.getPrice())
                .quantity(savedTradingOrder.getQuantity())
                .status(savedTradingOrder.getOrderStatus())
                .orderType(savedTradingOrder.getOrderType())
                .createdDate(LocalDateTime.now())
                .build();

        // Publish order through kafka producer
        tradingOrderProducer.sendOrder(tradingOrderEvents);

        //save to db and return a response of order
        return orderMapper.toOrderResponse(savedTradingOrder);
    }

    @Override
    public List<TradingOrderResponse> getAllOrders() {
        //Find all order from db
        List<TradingOrder> listOrder = tradingOrderRepository.findAll();
        List<TradingOrderResponse> responses = new ArrayList<>();
        listOrder.forEach(order -> {
            TradingOrderResponse tradingOrderResponse = tradingOrderMapper.toTradingOrderResponse(order);
            responses.add(tradingOrderResponse);
        });
        return responses;
    }

    @Override
    public TradingOrderResponse getTradingOrder(UUID tradingOrderId) {
        TradingOrder tradingOrder = orderRepository.findById(tradingOrderId).orElseThrow(
                () -> new IllegalArgumentException("Trading Order Not Found")
        );
        return tradingOrderMapper.toTradingOrderResponse(tradingOrder);
    }

    // consume new event status from kafka
    @KafkaListener(topics = "order-status", groupId = "order-status-udpated")
    public void comsumerOrderStatus(String message) {
        try {
            log.info("Received OrderStatus Event: {}", message);

            // use object mapper read value from string to dto class event
            TradingOrderStatusEvent orderStatusEvent = objectMapper.readValue(message, TradingOrderStatusEvent.class);

            // get dto event class id above to find order in db
            TradingOrder tradingOrder = tradingOrderRepository.findById(orderStatusEvent.getTradingOrderId()).orElseThrow(
                    () -> new IllegalArgumentException("Trading Order Not Found")
            );

            // set new status for order if it has some changes
            tradingOrder.setOrderStatus(orderStatusEvent.getStatus());

            // save it to db after set new status
            tradingOrderRepository.save(tradingOrder);

            log.info("Updated order {} -> {}", tradingOrder.getTradingOrderId(), tradingOrder.getOrderStatus());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
