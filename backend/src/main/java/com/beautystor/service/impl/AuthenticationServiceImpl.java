package com.beautystor.service.impl;

import com.beautystor.dto.auth.LoginRequest;
import com.beautystor.dto.auth.LoginResponse;
import com.beautystor.dto.auth.RegisterRequest;
import com.beautystor.dto.auth.RefreshTokenRequest;
import com.beautystor.dto.auth.TokenRefreshResponse;
import com.beautystor.dto.user.CreateUserRequest;
import com.beautystor.enm.Role;
import com.beautystor.security.AuthenticatedUser;
import com.beautystor.security.CustomUserDetailsService;
import com.beautystor.security.JwtService;
import com.beautystor.service.AuthenticationService;
import com.beautystor.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword()));

        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        String role = user.getAuthorities().iterator().next().getAuthority().replaceFirst("^ROLE_", "");
        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                role,
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user));
    }

    @Override
    public LoginResponse register(RegisterRequest request) {
        userService.create(new CreateUserRequest(
                request.getEmail(),
                request.getPassword(),
                Role.USER.name(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                true));

        return authenticate(new LoginRequest(request.getEmail(), request.getPassword()));
    }

    @Override
    public TokenRefreshResponse refreshAccessToken(RefreshTokenRequest request) {
        try {
            AuthenticatedUser user = (AuthenticatedUser) userDetailsService.loadUserByUsername(
                    jwtService.extractUsername(request.getRefreshToken()));

            if (!jwtService.isRefreshTokenValid(request.getRefreshToken(), user)) {
                throw new BadCredentialsException("Invalid refresh token");
            }

            return new TokenRefreshResponse(jwtService.generateAccessToken(user));
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("Invalid refresh token", ex);
        }
    }
}
