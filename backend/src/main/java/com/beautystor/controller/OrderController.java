package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.order.CreateOrderRequest;
import com.beautystor.dto.order.OrderResponse;
import com.beautystor.dto.order.OrderSummaryResponse;
import com.beautystor.security.AuthenticatedUser;
import com.beautystor.service.OrderService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Commandes du client authentifié.")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> create(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(user.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderSummaryResponse>>> getAll(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderSummaryResponse> orders = orderService.getAll(user.getId(), pageable);
        return ResponseEntity.ok(new ApiResponse<>(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getById(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable long id) {
        OrderResponse order = orderService.getById(user.getId(), id);
        return ResponseEntity.ok(new ApiResponse<>(order));
    }
}
