package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    private long userId;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Full address is required")
    private String fullAddress;
    
    @Column(nullable = false)
    @NotBlank(message = "City is required")
    private String city;
    
    @Column(name = "postalCode")
    private String postalCode;
    
    @Column(nullable = false)
    @NotBlank(message = "Phone is required")
    private String phone;
    
    @Column(nullable = false)
    private boolean isDefault;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
