package com.example.baserestapi.controller;

import com.example.baserestapi.dto.OrderRequest;
import com.example.baserestapi.dto.OrderResponse;
import com.example.baserestapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
        summary = "Create a new order",
        description = "Creates a new order with the provided details"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Order created successfully",
        content = @Content(schema = @Schema(implementation = OrderResponse.class))
    )
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get order by ID",
        description = "Retrieves an order by its ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Order found",
        content = @Content(schema = @Schema(implementation = OrderResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Order not found"
    )
    public ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping
    @Operation(
        summary = "Get all orders",
        description = "Retrieves all orders in the system"
    )
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/{id}/status")
    @Operation(
        summary = "Update order status",
        description = "Updates the status of an existing order"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Order status updated successfully",
        content = @Content(schema = @Schema(implementation = OrderResponse.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Order not found"
    )
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "New order status", required = true)
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete order",
        description = "Deletes an order by its ID"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Order deleted successfully"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Order not found"
    )
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
} 