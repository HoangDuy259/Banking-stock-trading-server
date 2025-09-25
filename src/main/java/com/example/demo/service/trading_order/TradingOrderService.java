package com.example.demo.service.trading_order;

import com.example.demo.dto.request.tradingOrder.TradingOrderRequest;
import com.example.demo.dto.response.stock.StockQuoteResponse;
import com.example.demo.dto.response.tradingOrder.TradingOrderResponse;
import com.example.demo.entity.event.TradingOrderEvents;
import com.example.demo.entity.stock.Stock;
import com.example.demo.entity.trading_account.TradingAccount;
import com.example.demo.entity.trading_transaction.TradingOrder;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.TradingOrderMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.repository.TradingOrderRepository;
import com.example.demo.service.stock_quote.StockQuoteService;
import com.example.demo.service.trading_order_producer.TradingOrderProducer;
import com.example.demo.utils.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

        //Validate trading account balance if it has enough to place an order
        //skip transaction fee of stock exchange
//        BigDecimal requireAmmount = price.multiply(
//                BigDecimal.valueOf(request.getQuantity()));
//
//
//        BigDecimal balance = tradingAccount.getCashBalance();
//        if(balance.compareTo(requireAmmount) < 0) {
//            throw new IllegalArgumentException("Your balance is not enough to place this order");
//        }


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

        // Publish order through kafka producer
        TradingOrderEvents tradingOrderEvents = new TradingOrderEvents();
        tradingOrderEvents.setTradingOrderId(savedTradingOrder.getTradingOrderId());
        tradingOrderEvents.setTradingAccountId(savedTradingOrder.getTradingAccount().getTradingAccountId());
        tradingOrderEvents.setStockId(savedTradingOrder.getStock().getStockId());
        tradingOrderEvents.setPrice(savedTradingOrder.getPrice());
        tradingOrderEvents.setQuantity(savedTradingOrder.getQuantity());
        tradingOrderEvents.setStatus(savedTradingOrder.getOrderStatus());
        tradingOrderEvents.setCreatedDate(LocalDateTime.now());
        tradingOrderProducer.sendOrder(tradingOrderEvents);

        //save to db and return a response of order
        return orderMapper.toOrderResponse(savedTradingOrder);
    }

    @Override
    public List<TradingOrderResponse> getAllOrders() {
        List<TradingOrder> listOrder = tradingOrderRepository.findAll();
        List<TradingOrderResponse> responses = new ArrayList<>();
        listOrder.forEach(order -> {
            TradingOrderResponse tradingOrderResponse = tradingOrderMapper.toTradingOrderResponse(order);
            responses.add(tradingOrderResponse);
        });
        return responses;
    }
}
