package com.beautystor.dto.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête de mise à jour de marque.")
public class UpdateBrandRequest {

    @NotBlank(message = "Brand name is required")
    private String name;

    @NotBlank(message = "Brand slug is required")
    private String slug;

    private String logoUrl;
}
