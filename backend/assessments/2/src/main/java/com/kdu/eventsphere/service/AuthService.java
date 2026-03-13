package com.kdu.eventsphere.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kdu.eventsphere.security.JwtUtil;


@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                                 JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateAndGenerateToken(String username, String password) {
        logger.debug("Authenticating user: {}", username);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
        
        logger.debug("Authentication successful for user: {}", username);

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", ""); // Remove ROLE_ prefix before storing in JWT
        
        logger.debug("Generating JWT token for user: {} with role: {}", username, role);
        return jwtUtil.generateToken(username, role);
    }
}
