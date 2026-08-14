package com.beautystor.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête de mise à jour du statut d'un utilisateur.")
public class UpdateUserStatusRequest {

    @NotNull(message = "Active is required")
    private Boolean active;
}
