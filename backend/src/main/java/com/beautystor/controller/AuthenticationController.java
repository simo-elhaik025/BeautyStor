package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.auth.LoginRequest;
import com.beautystor.dto.auth.LoginResponse;
import com.beautystor.dto.auth.RegisterRequest;
import com.beautystor.dto.auth.RefreshTokenRequest;
import com.beautystor.dto.auth.TokenRefreshResponse;
import com.beautystor.service.AuthenticationService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentification et renouvellement de jetons.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (AuthenticationException ex) {
            ApiResponse<LoginResponse> response = new ApiResponse<>(
                    List.of(new ApiResponse.ErrorItem("Invalid email or password")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        try {
            TokenRefreshResponse response = authenticationService.refreshAccessToken(request);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (AuthenticationException ex) {
            ApiResponse<TokenRefreshResponse> response = new ApiResponse<>(
                    List.of(new ApiResponse.ErrorItem("Invalid refresh token")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
