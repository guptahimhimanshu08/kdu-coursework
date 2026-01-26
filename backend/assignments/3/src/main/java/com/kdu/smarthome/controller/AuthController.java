package com.kdu.smarthome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kdu.smarthome.services.AuthService;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.dto.request.AuthRequest;
import com.kdu.smarthome.dto.request.SignupRequest;
import com.kdu.smarthome.dto.response.AuthResponse;
import com.kdu.smarthome.dto.response.SignupResponse;

@RestController
@RequestMapping(ApiEndpoints.Auth.BASE)
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authenticationService;

    public AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(ApiEndpoints.Auth.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        logger.info("Login attempt for user: {}", request.getUsername());

        String token = authenticationService.authenticateAndGenerateToken(
                request.getUsername(),
                request.getPassword()
        );
        
        logger.info("User {} successfully authenticated", request.getUsername());
        
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        logger.info("Signup request for username: {}", request.getUsername());
        
        User user = authenticationService.signup(request.getUsername(), request.getPassword());
        
        logger.info("User {} signed up successfully", request.getUsername());
        return ResponseEntity.ok(new SignupResponse(user.getUsername(), user.getCreatedDate(), user.getId()));
        
    }
}
