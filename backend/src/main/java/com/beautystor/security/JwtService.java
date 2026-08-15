package com.beautystor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    private final SecretKey signingKey;
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") Duration accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") Duration refreshTokenExpiration) {
        // Accept either a base64-encoded secret or a plain passphrase.
        // For plain passphrases, derive a 256-bit key using SHA-256 to guarantee sufficient strength for HMAC-SHA algorithms.
        SecretKey key;
        try {
            byte[] decoded = Decoders.BASE64.decode(secret);
            try {
                key = Keys.hmacShaKeyFor(decoded);
            } catch (io.jsonwebtoken.security.WeakKeyException e) {
                // Fallback to SHA-256-derived key if decoded key is too short
                java.security.MessageDigest digest;
                try {
                    digest = java.security.MessageDigest.getInstance("SHA-256");
                    key = Keys.hmacShaKeyFor(digest.digest(decoded));
                } catch (java.security.NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IllegalArgumentException ex) {
            // Not valid base64 — treat secret as passphrase and derive 256-bit key from UTF-8 bytes
            try {
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
                byte[] hashed = digest.digest(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                key = Keys.hmacShaKeyFor(hashed);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        this.signingKey = key;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, ACCESS_TOKEN_TYPE, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, REFRESH_TOKEN_TYPE, refreshTokenExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, ACCESS_TOKEN_TYPE);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return isTokenValid(token, userDetails, REFRESH_TOKEN_TYPE);
    }

    private String generateToken(UserDetails userDetails, String tokenType, Duration expiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(TOKEN_TYPE_CLAIM, tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    private boolean isTokenValid(String token, UserDetails userDetails, String expectedTokenType) {
        try {
            Claims claims = parseClaims(token);
            return userDetails.isEnabled()
                    && userDetails.getUsername().equals(claims.getSubject())
                    && expectedTokenType.equals(claims.get(TOKEN_TYPE_CLAIM, String.class));
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
