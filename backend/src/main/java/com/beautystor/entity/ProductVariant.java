package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "ProductVariant")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductVariant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "product_id", nullable = false)
    @NotNull(message = "Product ID is required")
    private long productId;
    
    @Column(nullable = false)
    @NotBlank(message = "SKU is required")
    private String sku;
    
    @Column(name = "displayName")
    private String displayName;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price is required")
    private BigDecimal price;
    
    @Column(name = "stockQuantity", nullable = false)
    @NotNull(message = "Stock quantity is required")
    private int stockQuantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    
    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
    
    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
