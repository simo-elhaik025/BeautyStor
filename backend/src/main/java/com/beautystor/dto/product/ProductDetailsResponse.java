package com.beautystor.dto.product;

import com.beautystor.dto.brand.BrandResponse;
import com.beautystor.dto.category.CategoryResponse;
import com.beautystor.dto.productimage.ProductImageResponse;
import com.beautystor.dto.productvariant.ProductVariantResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

    private long id;

    private String name;

    private String slug;

    private String description;

    private BrandResponse brand;

    private CategoryResponse category;

    private BigDecimal basePrice;

    private List<ProductImageResponse> images;

    private List<ProductVariantResponse> variants;

    private Boolean available;
}
