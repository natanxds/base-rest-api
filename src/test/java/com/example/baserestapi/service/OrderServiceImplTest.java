package com.example.baserestapi.service;

import com.example.baserestapi.domain.Order;
import com.example.baserestapi.domain.OrderItem;
import com.example.baserestapi.domain.OrderStatus;
import com.example.baserestapi.dto.OrderItemRequest;
import com.example.baserestapi.dto.OrderRequest;
import com.example.baserestapi.dto.OrderResponse;
import com.example.baserestapi.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderRequest orderRequest;
    private Order order;

    @BeforeEach
    void setUp() {
        Set<OrderItemRequest> itemRequests = new HashSet<>();
        itemRequests.add(new OrderItemRequest("Product 1", 2, BigDecimal.valueOf(10.00)));

        orderRequest = new OrderRequest("John Doe", itemRequests);

        Set<OrderItem> items = new HashSet<>();
        items.add(OrderItem.builder()
                .id(1L)
                .productName("Product 1")
                .quantity(2)
                .price(BigDecimal.valueOf(10.00))
                .build());

        order = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .status(OrderStatus.PENDING)
                .items(items)
                .build();
        order.recalculateTotal();
    }

    @Test
    void createOrder_ShouldReturnOrderResponse() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.createOrder(orderRequest);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.customerName()).isEqualTo("John Doe");
        assertThat(response.status()).isEqualTo(OrderStatus.PENDING);
        assertThat(response.totalAmount()).isEqualTo(BigDecimal.valueOf(20.00));
        assertThat(response.items()).hasSize(1);
    }

    @Test
    void getOrder_WithValidId_ShouldReturnOrderResponse() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse response = orderService.getOrder(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void getOrder_WithInvalidId_ShouldThrowEntityNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrder(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found with id: 1");
    }

    @Test
    void updateOrderStatus_WithValidIdAndStatus_ShouldReturnUpdatedOrderResponse() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.updateOrderStatus(1L, "CONFIRMED");

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(OrderStatus.CONFIRMED);
    }
} 