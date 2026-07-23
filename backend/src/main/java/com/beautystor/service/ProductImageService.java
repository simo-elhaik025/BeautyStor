package com.beautystor.service;

import com.beautystor.dto.productimage.CreateProductImageRequest;
import com.beautystor.dto.productimage.UpdateProductImageRequest;
import com.beautystor.dto.productimage.ProductImageResponse;

import java.util.List;

public interface ProductImageService {
    ProductImageResponse create(CreateProductImageRequest request);
    List<ProductImageResponse> getAll();
    ProductImageResponse getById(long id);
    ProductImageResponse update(long id, UpdateProductImageRequest request);
    void delete(long id);
}
