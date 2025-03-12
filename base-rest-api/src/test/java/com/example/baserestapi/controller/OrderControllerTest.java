package com.example.baserestapi.controller;

import com.example.baserestapi.domain.OrderStatus;
import com.example.baserestapi.dto.OrderItemRequest;
import com.example.baserestapi.dto.OrderItemResponse;
import com.example.baserestapi.dto.OrderRequest;
import com.example.baserestapi.dto.OrderResponse;
import com.example.baserestapi.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_WithValidRequest_ShouldReturnCreated() throws Exception {
        OrderRequest request = createOrderRequest();
        OrderResponse response = createOrderResponse();

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void getOrder_WithValidId_ShouldReturnOrder() throws Exception {
        OrderResponse response = createOrderResponse();
        when(orderService.getOrder(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void getOrder_WithInvalidId_ShouldReturnNotFound() throws Exception {
        when(orderService.getOrder(1L)).thenThrow(new EntityNotFoundException("Order not found with id: 1"));

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order not found with id: 1"));
    }

    @Test
    void getAllOrders_ShouldReturnOrdersList() throws Exception {
        List<OrderResponse> responses = List.of(createOrderResponse());
        when(orderService.getAllOrders()).thenReturn(responses);

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    void updateOrderStatus_WithValidIdAndStatus_ShouldReturnUpdatedOrder() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                "John Doe",
                LocalDateTime.now(),
                OrderStatus.CONFIRMED,
                createOrderResponse().items(),
                BigDecimal.valueOf(20.00)
        );
        when(orderService.updateOrderStatus(1L, "CONFIRMED")).thenReturn(response);

        mockMvc.perform(patch("/api/v1/orders/1/status")
                .param("status", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void deleteOrder_WithValidId_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrder_WithInvalidId_ShouldReturnNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Order not found with id: 1"))
                .when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isNotFound());
    }

    private OrderRequest createOrderRequest() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest("Product 1", 2, BigDecimal.valueOf(10.00)));
        return new OrderRequest("John Doe", items);
    }

    private OrderResponse createOrderResponse() {
        Set<OrderItemResponse> items = new HashSet<>();
        items.add(new OrderItemResponse(1L, "Product 1", 2, BigDecimal.valueOf(10.00)));
        
        return new OrderResponse(
                1L,
                "John Doe",
                LocalDateTime.now(),
                OrderStatus.PENDING,
                items,
                BigDecimal.valueOf(20.00)
        );
    }
} 