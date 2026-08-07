package com.beautystor.dto.order;

import com.beautystor.enm.OrderStatus;
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
public class OrderSummaryResponse {

    private long id;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private int totalItems;

    private BigDecimal totalPrice;
}
