package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.cart.AddCartItemRequest;
import com.beautystor.dto.cart.CartResponse;
import com.beautystor.dto.cart.UpdateCartItemRequest;
import com.beautystor.security.AuthenticatedUser;
import com.beautystor.service.CartService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Panier utilisateur.")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@AuthenticationPrincipal AuthenticatedUser user) {
        CartResponse cart = cartService.getCart(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addItem(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody AddCartItemRequest request) {
        CartResponse cart = cartService.addItem(user.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>(cart));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateItem(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable long itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        CartResponse cart = cartService.updateItem(user.getId(), itemId, request);
        return ResponseEntity.ok(new ApiResponse<>(cart));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable long itemId) {
        cartService.deleteItem(user.getId(), itemId);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
