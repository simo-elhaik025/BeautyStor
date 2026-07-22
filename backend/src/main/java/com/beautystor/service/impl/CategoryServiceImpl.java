package com.beautystor.service.impl;

import com.beautystor.dto.category.CreateCategoryRequest;
import com.beautystor.dto.category.UpdateCategoryRequest;
import com.beautystor.dto.category.CategoryResponse;
import com.beautystor.entity.Category;
import com.beautystor.repository.CategoryRepository;
import com.beautystor.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CreateCategoryRequest request) {
        validateParentCategory(request.getParentId());

        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setParentId(request.getParentId());
        category.setActive(request.getActive() != null ? request.getActive() : false);

        Category savedCategory = categoryRepository.save(category);

        return mapToCategoryResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found"));

        return mapToCategoryResponse(category);
    }

    @Override
    public CategoryResponse update(long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found"));

        validateParentCategory(request.getParentId());

        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setParentId(request.getParentId());
        category.setActive(request.getActive() != null ? request.getActive() : false);

        Category updatedCategory = categoryRepository.save(category);

        return mapToCategoryResponse(updatedCategory);
    }

    @Override
    public void delete(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

    private void validateParentCategory(Long parentId) {
        if (parentId != null) {
            boolean parentExists = categoryRepository.existsById(parentId);
            if (!parentExists) {
                throw new IllegalArgumentException("Parent category with ID " + parentId + " does not exist");
            }
        }
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setSlug(category.getSlug());
        response.setParentId(category.getParentId());
        response.setActive(category.isActive());
        return response;
    }
}
