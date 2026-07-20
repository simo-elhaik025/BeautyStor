package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Category name is required")
    private String name;
    
    @Column(nullable = false)
    @NotBlank(message = "Category slug is required")
    private String slug;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(nullable = false)
    private boolean isActive;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parent;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> children;
}
