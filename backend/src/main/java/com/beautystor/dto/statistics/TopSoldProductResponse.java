package com.beautystor.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopSoldProductResponse {

    private long productId;
    private String productName;
    private long quantitySold;
}
