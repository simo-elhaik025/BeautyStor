package com.beautystor.service.impl;

import com.beautystor.dto.cart.AddCartItemRequest;
import com.beautystor.dto.cart.CartResponse;
import com.beautystor.dto.cart.UpdateCartItemRequest;
import com.beautystor.entity.Cart;
import com.beautystor.entity.CartItem;
import com.beautystor.entity.ProductVariant;
import com.beautystor.mapper.CartMapper;
import com.beautystor.repository.CartItemRepository;
import com.beautystor.repository.CartRepository;
import com.beautystor.repository.ProductVariantRepository;
import com.beautystor.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private static final String ACTIVE_STATUS = "ACTIVE";

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponse getCart(long userId) {
        Cart cart = getOrCreateActiveCart(userId);
        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItem(long userId, AddCartItemRequest request) {
        Cart cart = getOrCreateActiveCart(userId);
        ProductVariant variant = getProductVariantOrThrow(request.getProductVariantId());
        CartItem existingItem = findItemByVariantId(cart, variant.getId());
        int requestedQuantity = request.getQuantity();

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + requestedQuantity;
            validateStock(variant, newQuantity);
            existingItem.setQuantity(newQuantity);
            existingItem.setSnapshotPrice(variant.getPrice());
            cartItemRepository.save(existingItem);
        } else {
            validateStock(variant, requestedQuantity);

            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductVariantId(variant.getId());
            cartItem.setQuantity(requestedQuantity);
            cartItem.setSnapshotPrice(variant.getPrice());
            cartItemRepository.save(cartItem);
        }

        return cartMapper.toResponse(getActiveCartOrThrow(userId));
    }

    @Override
    @Transactional
    public CartResponse updateItem(long userId, long itemId, UpdateCartItemRequest request) {
        Cart cart = getActiveCartOrThrow(userId);
        CartItem cartItem = findItemById(cart, itemId);
        ProductVariant variant = getProductVariantOrThrow(cartItem.getProductVariantId());

        validateStock(variant, request.getQuantity());

        cartItem.setQuantity(request.getQuantity());
        cartItem.setSnapshotPrice(variant.getPrice());
        cartItemRepository.save(cartItem);

        return cartMapper.toResponse(getActiveCartOrThrow(userId));
    }

    @Override
    @Transactional
    public CartResponse deleteItem(long userId, long itemId) {
        Cart cart = getActiveCartOrThrow(userId);
        CartItem cartItem = findItemById(cart, itemId);
        cartItemRepository.delete(cartItem);

        return cartMapper.toResponse(getActiveCartOrThrow(userId));
    }

    private Cart getOrCreateActiveCart(long userId) {
        return cartRepository.findFirstByUserIdAndStatusOrderByIdDesc(userId, ACTIVE_STATUS)
                .orElseGet(() -> createActiveCart(userId));
    }

    private Cart getActiveCartOrThrow(long userId) {
        return cartRepository.findFirstByUserIdAndStatusOrderByIdDesc(userId, ACTIVE_STATUS)
                .orElseThrow(() -> new EntityNotFoundException("Cart for user with ID " + userId + " not found"));
    }

    private Cart createActiveCart(long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(ACTIVE_STATUS);
        return cartRepository.save(cart);
    }

    private ProductVariant getProductVariantOrThrow(long productVariantId) {
        return productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new EntityNotFoundException("Product variant with ID " + productVariantId + " not found"));
    }

    private CartItem findItemByVariantId(Cart cart, long productVariantId) {
        if (cart.getItems() == null) {
            return null;
        }

        return cart.getItems().stream()
                .filter(item -> item.getProductVariantId() == productVariantId)
                .findFirst()
                .orElse(null);
    }

    private CartItem findItemById(Cart cart, long itemId) {
        if (cart.getItems() == null) {
            throw new EntityNotFoundException("Cart item with ID " + itemId + " not found");
        }

        return cart.getItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Cart item with ID " + itemId + " not found"));
    }

    private void validateStock(ProductVariant variant, int quantity) {
        if (quantity > variant.getStockQuantity()) {
            throw new IllegalArgumentException(
                    "Insufficient stock for product variant ID " + variant.getId() +
                            ". Requested: " + quantity +
                            ", available: " + variant.getStockQuantity());
        }
    }
}
