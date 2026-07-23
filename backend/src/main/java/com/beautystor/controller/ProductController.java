package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.product.CreateProductRequest;
import com.beautystor.dto.product.UpdateProductRequest;
import com.beautystor.dto.product.ProductResponse;
import com.beautystor.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse productResponse = productService.create(request);
        ApiResponse<ProductResponse> response = new ApiResponse<>(productResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {
        List<ProductResponse> products = productService.getAll();
        ApiResponse<List<ProductResponse>> response = new ApiResponse<>(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable long id) {
        ProductResponse productResponse = productService.getById(id);
        ApiResponse<ProductResponse> response = new ApiResponse<>(productResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse productResponse = productService.update(id, request);
        ApiResponse<ProductResponse> response = new ApiResponse<>(productResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
