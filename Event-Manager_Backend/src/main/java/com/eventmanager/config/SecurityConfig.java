package com.eventmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eventmanager.util.JwtFilter;

/**
 * Configuration de la sécurité pour l'application Spring Boot.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    private JwtFilter jwtFilter;
	
    // Configure les règles d'accès aux endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authentication/**").permitAll() // Accessible à tous
                .requestMatchers("/events/**").permitAll() // Accessible à tous
                .anyRequest().authenticated()
            )
            .httpBasic();
        
     // Ajoute le filtre JWT avant le filtre par défaut
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}