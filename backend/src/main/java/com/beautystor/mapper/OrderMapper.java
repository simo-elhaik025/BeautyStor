package com.beautystor.mapper;

import com.beautystor.dto.order.OrderItemResponse;
import com.beautystor.dto.order.OrderResponse;
import com.beautystor.dto.order.OrderSummaryResponse;
import com.beautystor.dto.order.ShippingAddressSnapshot;
import com.beautystor.entity.Order;
import com.beautystor.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ObjectMapper objectMapper;

    public OrderSummaryResponse toSummaryResponse(Order order) {
        List<OrderItem> items = safeItems(order);
        return new OrderSummaryResponse(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                totalItems(items),
                totalPrice(items));
    }

    public OrderResponse toResponse(Order order) {
        List<OrderItem> items = safeItems(order);
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getCreatedAt(),
                toShippingAddressSnapshot(order.getShippingAddressSnapshot()),
                items.stream().sorted(Comparator.comparingLong(OrderItem::getId)).map(this::toItemResponse).toList(),
                totalItems(items),
                totalPrice(items));
    }

    private OrderItemResponse toItemResponse(OrderItem orderItem) {
        BigDecimal unitPrice = orderItem.getPriceAtPurchase();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        String productName = orderItem.getProductNameSnapshot();
        String sku = orderItem.getProductVariantSku();
        String imageUrl = orderItem.getProductImageUrlSnapshot();

        return new OrderItemResponse(
                orderItem.getProductVariantId(),
                productName,
                sku,
                imageUrl,
                orderItem.getQuantity(),
                unitPrice,
                subtotal);
    }

    private ShippingAddressSnapshot toShippingAddressSnapshot(String shippingAddressSnapshot) {
        if (shippingAddressSnapshot == null || shippingAddressSnapshot.isBlank()) {
            return null;
        }

        try {
            return objectMapper.readValue(shippingAddressSnapshot, ShippingAddressSnapshot.class);
        } catch (JacksonException ex) {
            throw new IllegalStateException("Invalid shipping address snapshot", ex);
        }
    }

    private List<OrderItem> safeItems(Order order) {
        return order.getItems() == null ? new ArrayList<>() : order.getItems();
    }

    private int totalItems(List<OrderItem> items) {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    private BigDecimal totalPrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
