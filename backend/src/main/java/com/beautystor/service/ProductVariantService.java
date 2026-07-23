package com.beautystor.service;

import com.beautystor.dto.productvariant.CreateProductVariantRequest;
import com.beautystor.dto.productvariant.UpdateProductVariantRequest;
import com.beautystor.dto.productvariant.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {
    ProductVariantResponse create(CreateProductVariantRequest request);
    List<ProductVariantResponse> getAll();
    ProductVariantResponse getById(long id);
    ProductVariantResponse update(long id, UpdateProductVariantRequest request);
    void delete(long id);
}
