package com.kdu.smarthome.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final SecretKey signingKey;
    private final long expirationMs ;

    public JwtUtil(
                @Value("${jwt.secret}") String secret,
                @Value("${jwt.expiration-ms}") long expirationMs) {
        
        logger.info("Initializing JwtUtil with secret length: {}", secret.length());
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        logger.info("JWT expiration time set to {} ms", expirationMs);
    }

    public String generateToken(String username, String role) {
        logger.debug("Generating JWT token for username: {} with role: {}", username, role);
        String token = Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey)
                .compact();
        logger.debug("JWT token generated successfully for username: {}", username);
        return token;
    }

     public String extractUsername(String token) {

        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            logger.debug("Token validation successful");
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.warn("Token validation failed: {}", ex.getMessage());
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
