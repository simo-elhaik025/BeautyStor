package com.beautystor.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private long cartId;

    private List<CartItemResponse> items;

    private int totalItems;

    private BigDecimal totalPrice;
}
