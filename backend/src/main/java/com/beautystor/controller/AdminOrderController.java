package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.order.AdminOrderResponse;
import com.beautystor.dto.order.UpdateOrderStatusRequest;
import com.beautystor.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminOrderResponse>>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AdminOrderResponse> orders = orderService.getAllForAdmin(pageable);
        return ResponseEntity.ok(new ApiResponse<>(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminOrderResponse>> getById(@PathVariable long id) {
        AdminOrderResponse order = orderService.getByIdForAdmin(id);
        return ResponseEntity.ok(new ApiResponse<>(order));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AdminOrderResponse>> updateStatus(
            @PathVariable long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        AdminOrderResponse order = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(new ApiResponse<>(order));
    }
}
