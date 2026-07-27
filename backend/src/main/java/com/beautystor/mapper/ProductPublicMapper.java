package com.beautystor.mapper;

import com.beautystor.dto.brand.BrandResponse;
import com.beautystor.dto.category.CategoryResponse;
import com.beautystor.dto.product.ProductDetailsResponse;
import com.beautystor.dto.product.ProductSummaryResponse;
import com.beautystor.dto.productimage.ProductImageResponse;
import com.beautystor.dto.productvariant.ProductVariantResponse;
import com.beautystor.entity.Brand;
import com.beautystor.entity.Category;
import com.beautystor.entity.Product;
import com.beautystor.entity.ProductImage;
import com.beautystor.entity.ProductVariant;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class ProductPublicMapper {

    public ProductSummaryResponse toSummaryResponse(Product product) {
        ProductSummaryResponse response = new ProductSummaryResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSlug(product.getSlug());
        response.setBrand(toBrandResponse(product.getBrand()));
        response.setCategory(toCategoryResponse(product.getCategory()));
        response.setBasePrice(product.getBasePrice());
        response.setPrimaryImage(product.getImages().stream()
                .filter(ProductImage::isPrimary)
                .min(Comparator.comparingInt(ProductImage::getSortOrder))
                .map(this::toProductImageResponse)
                .orElse(null));
        response.setAvailable(product.isAvailable());
        return response;
    }

    public ProductDetailsResponse toDetailsResponse(Product product) {
        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setSlug(product.getSlug());
        response.setDescription(product.getDescription());
        response.setBrand(toBrandResponse(product.getBrand()));
        response.setCategory(toCategoryResponse(product.getCategory()));
        response.setBasePrice(product.getBasePrice());
        response.setImages(product.getImages().stream()
                .sorted(Comparator.comparingInt(ProductImage::getSortOrder))
                .map(this::toProductImageResponse)
                .toList());
        response.setVariants(product.getVariants().stream()
                .map(this::toProductVariantResponse)
                .toList());
        response.setAvailable(product.isAvailable());
        return response;
    }

    private BrandResponse toBrandResponse(Brand brand) {
        BrandResponse response = new BrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());
        response.setSlug(brand.getSlug());
        response.setLogoUrl(brand.getLogoUrl());
        return response;
    }

    private CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setSlug(category.getSlug());
        response.setParentId(category.getParentId());
        response.setActive(category.isActive());
        return response;
    }

    private ProductImageResponse toProductImageResponse(ProductImage productImage) {
        ProductImageResponse response = new ProductImageResponse();
        response.setId(productImage.getId());
        response.setProductId(productImage.getProductId());
        response.setUrl(productImage.getUrl());
        response.setSortOrder(productImage.getSortOrder());
        response.setPrimary(productImage.isPrimary());
        return response;
    }

    private ProductVariantResponse toProductVariantResponse(ProductVariant productVariant) {
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
