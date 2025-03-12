package com.example.baserestapi.dto;

import com.example.baserestapi.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponse(
    Long id,
    String customerName,
    LocalDateTime orderDate,
    OrderStatus status,
    Set<OrderItemResponse> items,
    BigDecimal totalAmount
) {} 