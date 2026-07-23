package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.productimage.CreateProductImageRequest;
import com.beautystor.dto.productimage.UpdateProductImageRequest;
import com.beautystor.dto.productimage.ProductImageResponse;
import com.beautystor.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductImageResponse>> create(@Valid @RequestBody CreateProductImageRequest request) {
        ProductImageResponse productImageResponse = productImageService.create(request);
        ApiResponse<ProductImageResponse> response = new ApiResponse<>(productImageResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getAll() {
        List<ProductImageResponse> productImages = productImageService.getAll();
        ApiResponse<List<ProductImageResponse>> response = new ApiResponse<>(productImages);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductImageResponse>> getById(@PathVariable long id) {
        ProductImageResponse productImageResponse = productImageService.getById(id);
        ApiResponse<ProductImageResponse> response = new ApiResponse<>(productImageResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductImageResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateProductImageRequest request) {
        ProductImageResponse productImageResponse = productImageService.update(id, request);
        ApiResponse<ProductImageResponse> response = new ApiResponse<>(productImageResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productImageService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
