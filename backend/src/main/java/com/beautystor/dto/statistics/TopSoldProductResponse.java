package com.beautystor.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Produit le plus vendu.")
public class TopSoldProductResponse {

    private long productId;
    private String productName;
    private long quantitySold;
}
