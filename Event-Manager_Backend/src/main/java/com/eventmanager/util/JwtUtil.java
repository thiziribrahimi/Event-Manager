package com.eventmanager.util;

import java.util.Date;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe utilitaire pour la gestion des tokens JWT.
 */
@Component
public class JwtUtil {

	// Durée de validité du token 1 heure en millisecondes
    private long expirationTime = 3600000;
    
    // Clé secrète pour signer les tokens
    private String secret = "6318e3b6-30f9-416f-a10b-b94b51b6a696";

	/**
     * Génère un token JWT pour un email donné
     *
     * @param email Email de l'utilisateur
     * @return Le token JWT
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Définit le sujet du token (l'email)
                .setIssuedAt(new Date()) // Date d'émission
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Date d'expiration
                .signWith(SignatureAlgorithm.HS512, secret) // Algorithme et clé secrète
                .compact();
    }
}