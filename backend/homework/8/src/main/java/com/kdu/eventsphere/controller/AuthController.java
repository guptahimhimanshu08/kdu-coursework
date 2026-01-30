package com.kdu.eventsphere.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kdu.eventsphere.dto.request.AuthRequest;
import com.kdu.eventsphere.dto.response.AuthResponse;
import com.kdu.eventsphere.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authenticationService;

    public AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        logger.info("Login attempt for user: {}", request.getUsername());
        String token = authenticationService.authenticateAndGenerateToken(
                request.getUsername(),
                request.getPassword()
        );
        logger.info("User {} successfully authenticated", request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
