package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.category.CreateCategoryRequest;
import com.beautystor.dto.category.UpdateCategoryRequest;
import com.beautystor.dto.category.CategoryResponse;
import com.beautystor.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.create(request);
        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        List<CategoryResponse> categories = categoryService.getAll();
        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>(categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable long id) {
        CategoryResponse categoryResponse = categoryService.getById(id);
        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable long id, @Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.update(id, request);
        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok(new ApiResponse<>((Void) null));
    }
}
