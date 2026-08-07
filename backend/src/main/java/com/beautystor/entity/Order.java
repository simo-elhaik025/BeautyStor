package com.beautystor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.beautystor.enm.OrderStatus;


@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Order number is required")
    private String orderNumber;
    
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    private long userId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status is required")
    private OrderStatus status;

    @Column(name = "shippingAddressSnapshot", columnDefinition = "JSON", nullable = false)
    @NotBlank(message = "Shipping address snapshot is required")
    private String shippingAddressSnapshot;
    
    @Column(name = "paymentMethod")
    private String paymentMethod;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;
    
    @Column(name = "deliveryNotes")
    private String deliveryNotes;
    
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @NotNull(message = "Created at is required")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items;
}
