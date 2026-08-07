package com.beautystor.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private long productVariantId;

    private String productName;

    private String sku;
    
    private String imageUrl;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
