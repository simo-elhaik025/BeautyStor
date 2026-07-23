package com.beautystor.service.impl;

import com.beautystor.dto.productimage.CreateProductImageRequest;
import com.beautystor.dto.productimage.UpdateProductImageRequest;
import com.beautystor.dto.productimage.ProductImageResponse;
import com.beautystor.entity.ProductImage;
import com.beautystor.repository.ProductImageRepository;
import com.beautystor.repository.ProductRepository;
import com.beautystor.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductImageResponse create(CreateProductImageRequest request) {
        validateProduct(request.getProductId());

        ProductImage productImage = new ProductImage();
        productImage.setProductId(request.getProductId());
        productImage.setUrl(request.getUrl());
        productImage.setSortOrder(request.getSortOrder());
        productImage.setPrimary(request.getPrimary() != null ? request.getPrimary() : false);

        ProductImage savedProductImage = productImageRepository.save(productImage);

        return mapToProductImageResponse(savedProductImage);
    }

    @Override
    public List<ProductImageResponse> getAll() {
        return productImageRepository.findAll()
                .stream()
                .map(this::mapToProductImageResponse)
                .toList();
    }

    @Override
    public ProductImageResponse getById(long id) {
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductImage with ID " + id + " not found"));

        return mapToProductImageResponse(productImage);
    }

    @Override
    public ProductImageResponse update(long id, UpdateProductImageRequest request) {
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductImage with ID " + id + " not found"));

        validateProduct(request.getProductId());

        productImage.setProductId(request.getProductId());
        productImage.setUrl(request.getUrl());
        productImage.setSortOrder(request.getSortOrder());
        productImage.setPrimary(request.getPrimary() != null ? request.getPrimary() : false);

        ProductImage updatedProductImage = productImageRepository.save(productImage);

        return mapToProductImageResponse(updatedProductImage);
    }

    @Override
    public void delete(long id) {
        if (!productImageRepository.existsById(id)) {
            throw new IllegalArgumentException("ProductImage with ID " + id + " not found");
        }
        productImageRepository.deleteById(id);
    }

    private void validateProduct(Long productId) {
        if (productId != null) {
            boolean productExists = productRepository.existsById(productId);
            if (!productExists) {
                throw new IllegalArgumentException("Product with ID " + productId + " does not exist");
            }
        }
    }

    private ProductImageResponse mapToProductImageResponse(ProductImage productImage) {
        ProductImageResponse response = new ProductImageResponse();
        response.setId(productImage.getId());
        response.setProductId(productImage.getProductId());
        response.setUrl(productImage.getUrl());
        response.setSortOrder(productImage.getSortOrder());
        response.setPrimary(productImage.isPrimary());
        return response;
    }
}
