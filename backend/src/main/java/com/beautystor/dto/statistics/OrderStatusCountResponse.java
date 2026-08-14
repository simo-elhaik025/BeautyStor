package com.beautystor.dto.statistics;

import com.beautystor.enm.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusCountResponse {

    private OrderStatus status;
    private long count;
}
