package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "OrderItem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "order_id", nullable = false)
    @NotNull(message = "Order ID is required")
    private long orderId;
    
    @Column(name = "product_variant_id", nullable = false)
    @NotNull(message = "Product variant ID is required")
    private long productVariantId;
    
    @Column(nullable = false)
    @NotBlank(message = "Product name snapshot is required")
    private String productNameSnapshot;
    
    @Column(nullable = false)
    @NotBlank(message = "Product variant SKU is required")
    private String productVariantSku;
    
    @Column(nullable = false)
    @NotNull(message = "Quantity is required")
    private int quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price at purchase is required")
    private BigDecimal priceAtPurchase;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant variant;
}
