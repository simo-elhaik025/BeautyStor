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
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Product name is required")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Product slug is required")
    private String slug;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "brand_id", nullable = false)
    @NotNull(message = "Brand ID is required")
    private long brandId;
    
    @Column(name = "category_id", nullable = false)
    @NotNull(message = "Category ID is required")
    private long categoryId;
    
    @Column(name = "basePrice", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Base price is required")
    private BigDecimal basePrice;
    
    @Column(nullable = false)
    private boolean isAvailable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> variants;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> images;
}
