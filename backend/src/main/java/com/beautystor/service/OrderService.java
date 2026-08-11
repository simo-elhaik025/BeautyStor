package com.beautystor.service;

import com.beautystor.dto.order.AdminOrderResponse;
import com.beautystor.dto.order.CreateOrderRequest;
import com.beautystor.dto.order.OrderResponse;
import com.beautystor.dto.order.OrderSummaryResponse;
import com.beautystor.enm.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse createOrder(long userId, CreateOrderRequest request);

    Page<OrderSummaryResponse> getAll(long userId, Pageable pageable);

    OrderResponse getById(long userId, long orderId);

    Page<AdminOrderResponse> getAllForAdmin(Pageable pageable);

    AdminOrderResponse getByIdForAdmin(long orderId);

    AdminOrderResponse updateOrderStatus(long orderId, OrderStatus status);
}
