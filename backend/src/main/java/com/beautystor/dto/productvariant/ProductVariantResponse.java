package com.beautystor.dto.productvariant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'une variante produit.")
public class ProductVariantResponse {

    private long id;

    private long productId;

    private String sku;

    private String displayName;

    private BigDecimal price;

    private int stockQuantity;
}
