package com.beautystor.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Schema(description = "Réponse de renouvellement du jeton d'accès.")
public class TokenRefreshResponse {

    private final String accessToken;
}
