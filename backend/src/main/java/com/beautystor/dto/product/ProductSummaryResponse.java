package com.beautystor.dto.product;

import com.beautystor.dto.brand.BrandResponse;
import com.beautystor.dto.category.CategoryResponse;
import com.beautystor.dto.productimage.ProductImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {

    private long id;

    private String name;

    private String slug;

    private BrandResponse brand;

    private CategoryResponse category;

    private BigDecimal basePrice;

    private ProductImageResponse primaryImage;

    private Boolean available;
}
