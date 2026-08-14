package com.beautystor.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Schema(description = "Réponse de connexion contenant les jetons JWT.")
public class LoginResponse {

    private final long id;
    private final String email;
    private final String role;
    private final String accessToken;
    private final String refreshToken;
}
