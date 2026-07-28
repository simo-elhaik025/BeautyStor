package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.product.ProductDetailsResponse;
import com.beautystor.dto.product.ProductSummaryResponse;
import com.beautystor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
    public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductSummaryResponse>>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Long brand,
            @RequestParam(required = false) Boolean available,
            Pageable pageable) {
        Page<ProductSummaryResponse> products = productService.getAll(search, category, brand, available, pageable);
        ApiResponse<Page<ProductSummaryResponse>> response = new ApiResponse<>(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> getBySlug(@PathVariable String slug) {
        ProductDetailsResponse productResponse = productService.getBySlug(slug);
        ApiResponse<ProductDetailsResponse> response = new ApiResponse<>(productResponse);
        return ResponseEntity.ok(response);
    }
}
