package com.beautystor.service;

import com.beautystor.dto.product.CreateProductRequest;
import com.beautystor.dto.product.ProductDetailsResponse;
import com.beautystor.dto.product.UpdateProductRequest;
import com.beautystor.dto.product.ProductResponse;
import com.beautystor.dto.product.ProductSummaryResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    List<ProductSummaryResponse> getAll();
    ProductDetailsResponse getBySlug(String slug);
    List<ProductResponse> getAllForAdmin();
    ProductResponse getByIdForAdmin(long id);
    ProductResponse update(long id, UpdateProductRequest request);
    void delete(long id);
}
