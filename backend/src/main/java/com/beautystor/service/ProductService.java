package com.beautystor.service;

import com.beautystor.dto.product.CreateProductRequest;
import com.beautystor.dto.product.UpdateProductRequest;
import com.beautystor.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(CreateProductRequest request);
    List<ProductResponse> getAll();
    ProductResponse getById(long id);
    ProductResponse update(long id, UpdateProductRequest request);
    void delete(long id);
}
