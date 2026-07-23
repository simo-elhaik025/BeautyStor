package com.beautystor.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
