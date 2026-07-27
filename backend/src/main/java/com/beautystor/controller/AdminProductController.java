package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.product.ProductResponse;
import com.beautystor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {
        List<ProductResponse> products = productService.getAllForAdmin();
        return ResponseEntity.ok(new ApiResponse<>(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable long id) {
        ProductResponse product = productService.getByIdForAdmin(id);
        return ResponseEntity.ok(new ApiResponse<>(product));
    }
}
