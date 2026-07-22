package com.beautystor.service;

import com.beautystor.dto.category.CreateCategoryRequest;
import com.beautystor.dto.category.UpdateCategoryRequest;
import com.beautystor.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CreateCategoryRequest request);
    List<CategoryResponse> getAll();
    CategoryResponse getById(long id);
    CategoryResponse update(long id, UpdateCategoryRequest request);
    void delete(long id);
}
