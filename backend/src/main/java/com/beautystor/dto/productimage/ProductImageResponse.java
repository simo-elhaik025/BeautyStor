package com.beautystor.dto.productimage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'une image produit.")
public class ProductImageResponse {

    private long id;

    private long productId;

    private String url;

    private int sortOrder;

    private Boolean primary;
}
