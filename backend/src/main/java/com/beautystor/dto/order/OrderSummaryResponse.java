package com.beautystor.dto.order;

import com.beautystor.enm.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Résumé d'une commande côté client.")
public class OrderSummaryResponse {

    private long id;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private int totalItems;

    private BigDecimal totalPrice;
}
