package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Brand")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Brand name is required")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Brand slug is required")
    private String slug;
    
    @Column(name = "logoUrl")
    private String logoUrl;
    
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;
}
