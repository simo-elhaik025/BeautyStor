package com.beautystor.dto.statistics;

import com.beautystor.enm.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Répartition des commandes par statut.")
public class OrderStatusCountResponse {

    private OrderStatus status;
    private long count;
}
