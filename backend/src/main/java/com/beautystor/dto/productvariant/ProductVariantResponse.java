package com.beautystor.dto.productvariant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse {

    private long id;

    private long productId;

    private String sku;

    private String displayName;

    private BigDecimal price;

    private int stockQuantity;
}
