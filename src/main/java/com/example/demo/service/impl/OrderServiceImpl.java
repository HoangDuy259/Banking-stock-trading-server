package com.example.demo.service.impl;

import com.example.demo.dto.request.stocks.TradingOrderRequestDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.Portfolio;
import com.example.demo.entity.Stock;
import com.example.demo.entity.TradingAccount;
import com.example.demo.exception.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.TradingAccountRepository;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final TradingAccountRepository tradingAccountRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public Order buyOrder(TradingOrderRequestDto request) {
        TradingAccount tradingAccount = tradingAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ExistsException("Trading account not found with ID"));
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new ExistsException("Stock not found with ticker" ));
        BigDecimal totalCost = request.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        OrderSide orderSide = request.getOrderSide();
        //Mua
        if (orderSide.equals(OrderSide.BUY)) {
            if (tradingAccount.getCashBalance().compareTo(totalCost) < 0) {
                throw new ExistsException("Insufficient balance to place order");
            }
            //tru tien
            tradingAccount.setCashBalance(tradingAccount.getCashBalance().subtract(totalCost));
            tradingAccountRepository.save(tradingAccount);
        } else if (orderSide.equals(OrderSide.SELL)) {
            Portfolio portfolio = portfolioRepository.findByAccountAndStock(tradingAccount,stock)
                    .orElseThrow(() -> new ExistsException("Stock not found with ticker" ));
            if (portfolio.getQuantity() < request.getQuantity()) {
                throw new ExistsException("Not enough stock quantity to sell");
            }
            portfolio.setQuantity(portfolio.getQuantity() - request.getQuantity());
            portfolioRepository.save(portfolio);
        } else {
            throw new ExistsException("Invalid order side ");
        }
        Order order = new Order();
        order.setAccount(tradingAccount);
        order.setStock(stock);
        order.setOrderSide(orderSide); // enum BUY and SELL
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setMatchedQuantity(0L);
        order.setStatus(OrderStatus.PENDING); // enum PENDING

        //Kiểm tra khop lenh
        Optional<Order> matchOrder = orderRepository.findMatchingOrder(
                stock.getStockId(),
                orderSide == OrderSide.BUY ? OrderSide.SELL : OrderSide.BUY,
                request.getPrice(),
                request.getQuantity(),
                tradingAccount.getAccountId()
        );
        if (matchOrder.isPresent()) {
            Order matchedOrder = matchOrder.get();

            // Cập nhật trạng thái nếu khớp
            order.setStatus(OrderStatus.FILLED);
            matchedOrder.setStatus(OrderStatus.FILLED);

            orderRepository.save(order);
            orderRepository.save(matchedOrder);
        }
        return orderRepository.save(order);
    }
    private void matchOrder(Order buyOrder, Order sellOrder) {
        TradingAccount buyAccount = buyOrder.getAccount();
        TradingAccount sellAccount = sellOrder.getAccount();
        Stock tradedStock = buyOrder.getStock();
        Long tradedQuantity = buyOrder.getQuantity();
        BigDecimal tradedPrice = buyOrder.getPrice();
        BigDecimal totalTradedAmount = tradedPrice.multiply(BigDecimal.valueOf(tradedQuantity));
        //1. Cap nhat nguoi mua
        Portfolio portfolio = portfolioRepository.findByAccountAndStock(buyAccount,tradedStock)
                .orElse(null);
        if (portfolio != null) {
            portfolio.setQuantity(portfolio.getQuantity() + tradedQuantity);
        } else {
            portfolio = new Portfolio();
            portfolio.setAccount(buyAccount);
            portfolio.setStock(tradedStock);
            portfolio.setQuantity(tradedQuantity);
        }
        portfolioRepository.save(portfolio);
        //2. Cap nhat nguoi ban
        sellAccount.setCashBalance(sellAccount.getCashBalance().add(totalTradedAmount));
        tradingAccountRepository.save(sellAccount);
    }
}
