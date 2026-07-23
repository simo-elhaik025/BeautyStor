package com.beautystor.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product slug is required")
    private String slug;

    private String description;

    @NotNull(message = "Brand ID is required")
    @Positive(message = "Brand ID must be a positive number")
    private Long brandId;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Long categoryId;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than 0")
    private BigDecimal basePrice;

    private Boolean available;
}
