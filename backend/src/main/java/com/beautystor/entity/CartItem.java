package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "CartItem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "cart_id", nullable = false)
    @NotNull(message = "Cart ID is required")
    private long cartId;
    
    @Column(name = "product_variant_id", nullable = false)
    @NotNull(message = "Product variant ID is required")
    private long productVariantId;
    
    @Column(nullable = false)
    @NotNull(message = "Quantity is required")
    private int quantity;
    
    @Column(name = "snapshotPrice", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Snapshot price is required")
    private BigDecimal snapshotPrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant variant;
}
