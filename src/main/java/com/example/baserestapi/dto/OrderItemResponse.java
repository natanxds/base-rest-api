package com.example.baserestapi.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    String productName,
    Integer quantity,
    BigDecimal price
) {} 