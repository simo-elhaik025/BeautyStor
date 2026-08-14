package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.productvariant.CreateProductVariantRequest;
import com.beautystor.dto.productvariant.UpdateProductVariantRequest;
import com.beautystor.dto.productvariant.ProductVariantResponse;
import com.beautystor.service.ProductVariantService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
@Tag(name = "Product Variants", description = "Gestion des variantes produits.")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> create(@Valid @RequestBody CreateProductVariantRequest request) {
        ProductVariantResponse productVariantResponse = productVariantService.create(request);
        ApiResponse<ProductVariantResponse> response = new ApiResponse<>(productVariantResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductVariantResponse>>> getAll() {
        List<ProductVariantResponse> productVariants = productVariantService.getAll();
        ApiResponse<List<ProductVariantResponse>> response = new ApiResponse<>(productVariants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> getById(@PathVariable long id) {
        ProductVariantResponse productVariantResponse = productVariantService.getById(id);
        ApiResponse<ProductVariantResponse> response = new ApiResponse<>(productVariantResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ProductVariantResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateProductVariantRequest request) {
        ProductVariantResponse productVariantResponse = productVariantService.update(id, request);
        ApiResponse<ProductVariantResponse> response = new ApiResponse<>(productVariantResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productVariantService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
