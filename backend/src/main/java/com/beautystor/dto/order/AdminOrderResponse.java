package com.beautystor.dto.order;

import com.beautystor.enm.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Détail d'une commande côté administration.")
public class AdminOrderResponse {

    private long id;

    private String orderNumber;

    private long userId;

    private String userEmail;

    private String userName;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private String paymentMethod;

    private String deliveryNotes;

    private ShippingAddressSnapshot shippingAddress;

    private List<AdminOrderItemResponse> items;

    private int totalItems;

    private BigDecimal totalPrice;
}
