package com.example.baserestapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record OrderRequest(
    @NotEmpty(message = "Customer name is required")
    String customerName,

    @NotNull
    @Size(min = 1, message = "Order must contain at least one item")
    Set<@Valid OrderItemRequest> items
) {} 