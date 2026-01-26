package com.kdu.smarthome.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return "/api/v1/auth/login".equals(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        log.debug("Processing authentication for request: {}", request.getRequestURI());
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No Bearer token found in request");
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = authHeader.substring(7);

        if (jwtUtil.isTokenValid(token)) {

            String username = jwtUtil.extractUsername(token);
            
            String role = jwtUtil.extractRole(token);
            
            log.debug("Valid JWT token for user: {} with role: {}", username, role);

            var authority = new SimpleGrantedAuthority("ROLE_"+role);

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(authority)
                    );

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authentication set for user: {}", username);
            }
        } else {
            log.warn("Invalid JWT token in request");
        }

        filterChain.doFilter(request, response);

    }

}