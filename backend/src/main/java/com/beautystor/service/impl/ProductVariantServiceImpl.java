package com.beautystor.service.impl;

import com.beautystor.dto.productvariant.CreateProductVariantRequest;
import com.beautystor.dto.productvariant.UpdateProductVariantRequest;
import com.beautystor.dto.productvariant.ProductVariantResponse;
import com.beautystor.entity.ProductVariant;
import com.beautystor.repository.ProductRepository;
import com.beautystor.repository.ProductVariantRepository;
import com.beautystor.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductVariantResponse create(CreateProductVariantRequest request) {
        validateProduct(request.getProductId());
        validateSkuUniqueness(request.getSku());

        ProductVariant productVariant = new ProductVariant();
        productVariant.setProductId(request.getProductId());
        productVariant.setSku(request.getSku());
        productVariant.setDisplayName(request.getDisplayName());
        productVariant.setPrice(request.getPrice());
        productVariant.setStockQuantity(request.getStockQuantity());

        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);

        return mapToProductVariantResponse(savedProductVariant);
    }

    @Override
    public List<ProductVariantResponse> getAll() {
        return productVariantRepository.findAll()
                .stream()
                .map(this::mapToProductVariantResponse)
                .toList();
    }

    @Override
    public ProductVariantResponse getById(long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductVariant with ID " + id + " not found"));

        return mapToProductVariantResponse(productVariant);
    }

    @Override
    public ProductVariantResponse update(long id, UpdateProductVariantRequest request) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProductVariant with ID " + id + " not found"));

        validateProduct(request.getProductId());
        validateSkuUniquenessForUpdate(request.getSku(), id);

        productVariant.setProductId(request.getProductId());
        productVariant.setSku(request.getSku());
        productVariant.setDisplayName(request.getDisplayName());
        productVariant.setPrice(request.getPrice());
        productVariant.setStockQuantity(request.getStockQuantity());

        ProductVariant updatedProductVariant = productVariantRepository.save(productVariant);

        return mapToProductVariantResponse(updatedProductVariant);
    }

    @Override
    public void delete(long id) {
        if (!productVariantRepository.existsById(id)) {
            throw new IllegalArgumentException("ProductVariant with ID " + id + " not found");
        }
        productVariantRepository.deleteById(id);
    }

    private void validateProduct(Long productId) {
        if (productId != null) {
            boolean productExists = productRepository.existsById(productId);
            if (!productExists) {
                throw new IllegalArgumentException("Product with ID " + productId + " does not exist");
            }
        }
    }

    private void validateSkuUniqueness(String sku) {
        boolean skuExists = productVariantRepository.existsBySku(sku);
        if (skuExists) {
            throw new IllegalArgumentException(
                "A product variant with SKU '" + sku + "' already exists. SKU must be unique."
            );
        }
    }

    private void validateSkuUniquenessForUpdate(String sku, long currentVariantId) {
        boolean skuExists = productVariantRepository.existsBySkuAndIdNot(sku, currentVariantId);
        if (skuExists) {
            throw new IllegalArgumentException(
                "A product variant with SKU '" + sku + "' already exists. SKU must be unique."
            );
        }
    }

    private ProductVariantResponse mapToProductVariantResponse(ProductVariant productVariant) {
        ProductVariantResponse response = new ProductVariantResponse();
        response.setId(productVariant.getId());
        response.setProductId(productVariant.getProductId());
        response.setSku(productVariant.getSku());
        response.setDisplayName(productVariant.getDisplayName());
        response.setPrice(productVariant.getPrice());
        response.setStockQuantity(productVariant.getStockQuantity());
        return response;
    }
}
