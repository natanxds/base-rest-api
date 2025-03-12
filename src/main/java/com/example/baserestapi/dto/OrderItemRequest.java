package com.example.baserestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequest(
    @NotEmpty(message = "Product name is required")
    String productName,

    @NotNull
    @Positive(message = "Quantity must be positive")
    Integer quantity,

    @NotNull
    @Positive(message = "Price must be positive")
    BigDecimal price
) {} 