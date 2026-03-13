package com.kdu.graphql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the GraphQL application.
 * Defines security policies, authentication mechanisms, and endpoint access rules.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    /**
     * Configures the security filter chain for HTTP requests.
     * Disables CSRF protection, form login, and HTTP Basic authentication.
     * Permits public access to GraphQL endpoints and documentation.
     * 
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        logger.info("Configuring security filter chain");
        
        http
            .csrf(csrf -> {
                csrf.disable();
                logger.debug("CSRF protection disabled");
            })

            .authorizeHttpRequests(auth -> {
                logger.debug("Configuring authorization rules");
                auth
                .requestMatchers("/auth/login", "/error","/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**", "/graphiql", "/graphql").permitAll()
                .anyRequest().authenticated();
            })           
            .formLogin(form -> {
                form.disable();
                logger.debug("Form login disabled");
            })
            .httpBasic(basic -> {
                basic.disable();
                logger.debug("HTTP Basic authentication disabled");
            })
            ;

        logger.info("Security filter chain configured successfully");
        return http.build();
    }
}
