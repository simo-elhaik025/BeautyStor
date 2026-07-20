package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "Cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    private long userId;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(nullable = false)
    @NotBlank(message = "Status is required")
    private String status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartItem> items;
}
