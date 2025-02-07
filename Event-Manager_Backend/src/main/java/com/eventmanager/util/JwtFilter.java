package com.eventmanager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventmanager.model.User;
import com.eventmanager.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtre pour valider le token JWT sur chaque requête.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        // Récupère l'en-tête "Authorization" de la requête
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Vérifie si l'en-tête contient un token Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        	// Retirer "Bearer " du token
        	token = authorizationHeader.substring(7);
        	
            // Extrait l'email du token
            email = jwtUtil.getEmailFromToken(token);
        }

        // Vérifie si l'email est présent et si l'utilisateur n'est pas déjà authentifié
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Charge l'utilisateur depuis la base de données
            User user = userService.findByEmail(email);

            // Vérifie si le token est valide
            if (jwtUtil.validateToken(token)) {
                // Crée le jeton d'authentification
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, null);

                // Définit l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Poursuit avec la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}