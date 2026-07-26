package com.beautystor.service;

import com.beautystor.dto.auth.LoginRequest;
import com.beautystor.dto.auth.LoginResponse;
import com.beautystor.dto.auth.RegisterRequest;
import com.beautystor.dto.auth.RefreshTokenRequest;
import com.beautystor.dto.auth.TokenRefreshResponse;

public interface AuthenticationService {

    LoginResponse authenticate(LoginRequest request);

    LoginResponse register(RegisterRequest request);

    TokenRefreshResponse refreshAccessToken(RefreshTokenRequest request);
}
