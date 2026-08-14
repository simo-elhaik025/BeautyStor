package com.beautystor.dto.product;

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
@Schema(description = "Représentation simple d'un produit.")
public class ProductResponse {

    private long id;

    private String name;

    private String slug;

    private String description;

    private long brandId;

    private long categoryId;

    private BigDecimal basePrice;

    private Boolean available;
}
