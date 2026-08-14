package com.beautystor.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Panier utilisateur.")
public class CartResponse {

    private long cartId;

    private List<CartItemResponse> items;

    private int totalItems;

    private BigDecimal totalPrice;
}
