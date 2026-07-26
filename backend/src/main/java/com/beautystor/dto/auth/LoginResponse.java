package com.beautystor.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private final long id;
    private final String email;
    private final String role;
    private final String accessToken;
    private final String refreshToken;
}
