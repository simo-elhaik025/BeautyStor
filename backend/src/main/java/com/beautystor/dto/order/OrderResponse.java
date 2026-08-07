package com.beautystor.dto.order;

import com.beautystor.enm.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private long id;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private ShippingAddressSnapshot shippingAddress;

    private List<OrderItemResponse> items;

    private int totalItems;

    private BigDecimal totalPrice;
}
