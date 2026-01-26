package com.kdu.smarthome.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;
import com.kdu.smarthome.security.JwtUtil;


@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                                 JwtUtil jwtUtil,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                .replace("ROLE_", ""); 
        
        logger.debug("Generating JWT token for user: {} with role: {}", username, role);
        return jwtUtil.generateToken(username, role);
    }

    public User signup(String username, String password){
        logger.info("Creating new user account: {}", username);
        
        User user = new User();
        
        user.setUsername(username);
        
        user.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        logger.info("User account created successfully: {}", username);
        return savedUser;
    }
}
