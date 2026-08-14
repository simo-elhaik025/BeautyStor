package com.beautystor.dto.order;

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
@Schema(description = "Article de commande côté administration.")
public class AdminOrderItemResponse {

    private long productVariantId;

    private String productName;

    private String sku;

    private String variantDisplayName;

    private String description;

    private String imageUrl;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
