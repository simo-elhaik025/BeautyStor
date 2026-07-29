package com.beautystor.service;

import com.beautystor.dto.cart.AddCartItemRequest;
import com.beautystor.dto.cart.CartResponse;
import com.beautystor.dto.cart.UpdateCartItemRequest;

public interface CartService {

    CartResponse getCart(long userId);

    CartResponse addItem(long userId, AddCartItemRequest request);

    CartResponse updateItem(long userId, long itemId, UpdateCartItemRequest request);

    CartResponse deleteItem(long userId, long itemId);
}
