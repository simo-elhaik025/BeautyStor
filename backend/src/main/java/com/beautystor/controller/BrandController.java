package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.brand.CreateBrandRequest;
import com.beautystor.dto.brand.UpdateBrandRequest;
import com.beautystor.dto.brand.BrandResponse;
import com.beautystor.service.BrandService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
@Tag(name = "Brands", description = "Gestion des marques.")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<BrandResponse>> create(@Valid @RequestBody CreateBrandRequest request) {
        BrandResponse brandResponse = brandService.create(request);
        ApiResponse<BrandResponse> response = new ApiResponse<>(brandResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAll() {
        List<BrandResponse> brands = brandService.getAll();
        ApiResponse<List<BrandResponse>> response = new ApiResponse<>(brands);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getById(@PathVariable long id) {
        BrandResponse brandResponse = brandService.getById(id);
        ApiResponse<BrandResponse> response = new ApiResponse<>(brandResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<BrandResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateBrandRequest request) {
        BrandResponse brandResponse = brandService.update(id, request);
        ApiResponse<BrandResponse> response = new ApiResponse<>(brandResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
