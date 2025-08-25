package com.example.demo.dto.response;

import com.example.demo.exception.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private UUID orderId;
    private OrderStatus status;
    private String message;
    private LocalDateTime createdAt;
}
