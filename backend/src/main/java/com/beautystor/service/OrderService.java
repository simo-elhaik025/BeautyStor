package com.beautystor.service;

import com.beautystor.dto.order.CreateOrderRequest;
import com.beautystor.dto.order.OrderResponse;
import com.beautystor.dto.order.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse createOrder(long userId, CreateOrderRequest request);

    Page<OrderSummaryResponse> getAll(long userId, Pageable pageable);

    OrderResponse getById(long userId, long orderId);
}
