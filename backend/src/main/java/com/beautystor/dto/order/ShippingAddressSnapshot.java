package com.beautystor.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Instantané d'adresse de livraison.")
public class ShippingAddressSnapshot {

    private String fullAddress;

    private String city;

    private String phone;
}
