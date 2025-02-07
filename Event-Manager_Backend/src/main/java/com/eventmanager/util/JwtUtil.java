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
    
    /**
     * Extrait l'email d'un token JWT.
     *
     * @param token Le token JWT
     * @return L'email contenu dans le token
     */
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valide un token JWT.
     *
     * @param token Le token JWT
     * @return true si le token est valide, false sinon
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true; // Le token est valide
        } catch (Exception e) {
            // Le token est invalide ou expiré
            return false;
        }
    }
}