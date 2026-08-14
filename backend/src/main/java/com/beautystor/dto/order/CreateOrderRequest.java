package com.beautystor.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête de création de commande.")
public class CreateOrderRequest {

    @NotBlank(message = "Full address is required")
    private String fullAddress;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Phone is required")
    private String phone;
}
