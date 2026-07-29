package com.beautystor.mapper;

import com.beautystor.dto.cart.CartItemResponse;
import com.beautystor.dto.cart.CartResponse;
import com.beautystor.entity.Cart;
import com.beautystor.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems() == null ? new ArrayList<>() : cart.getItems().stream()
                .sorted(Comparator.comparingLong(CartItem::getId))
                .map(this::toItemResponse)
                .toList();

        int totalItems = items.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(cart.getId(), items, totalItems, totalPrice);
    }

    private CartItemResponse toItemResponse(CartItem item) {
        BigDecimal unitPrice = item.getSnapshotPrice();
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        String productName = item.getVariant() != null && item.getVariant().getProduct() != null
                ? item.getVariant().getProduct().getName()
                : null;
        String sku = item.getVariant() != null ? item.getVariant().getSku() : null;

        return new CartItemResponse(
                item.getId(),
                item.getProductVariantId(),
                productName,
                sku,
                item.getQuantity(),
                unitPrice,
                subtotal);
    }
}
