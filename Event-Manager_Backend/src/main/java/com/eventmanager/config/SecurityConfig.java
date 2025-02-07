package com.eventmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de la sécurité pour l'application Spring Boot.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configure les règles d'accès aux endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authentication/**").permitAll() // Accessible à tous
                .anyRequest().authenticated()
            )
            .httpBasic();
        return http.build();
    }
}