package com.beautystor.dto.productimage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {

    private long id;

    private long productId;

    private String url;

    private int sortOrder;

    private Boolean primary;
}
