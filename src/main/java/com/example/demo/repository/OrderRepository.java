package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.exception.OrderSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.stock.stockId = :stockId " +
            "AND o.orderSide = :oppositeSide " +
            "AND o.price = :price " +
            "AND o.quantity = :quantity " +
            "AND o.status = 'PENDING' " +
            "AND o.account.accountId <> :accountId")
    Optional<Order> findMatchingOrder(@Param("stockId") UUID stockId,
                                      @Param("oppositeSide") OrderSide oppositeSide,
                                      @Param("price") BigDecimal price,
                                      @Param("quantity") Long quantity,
                                      @Param("accountId") UUID accountId);

}
