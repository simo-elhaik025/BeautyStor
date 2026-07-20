package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ProductImage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "product_id", nullable = false)
    @NotNull(message = "Product ID is required")
    private long productId;
    
    @Column(nullable = false)
    @NotBlank(message = "Image URL is required")
    private String url;
    
    @Column(name = "sortOrder", nullable = false)
    @NotNull(message = "Sort order is required")
    private int sortOrder;
    
    @Column(nullable = false)
    private boolean isPrimary;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
