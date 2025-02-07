package com.eventmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.eventmanager.model.User;
import com.eventmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired 
	private UserRepository userRepository;
	
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/**
     * Enregistre un nouvel utilisateur
     *
     * @param name     Nom de l'utilisateur
     * @param email    Email de l'utilisateur
     * @param password Mot de passe de l'utilisateur (en clair)
     * @return L'utilisateur enregistré ou null si l'email existe déjà
     */
    public User registerUser(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
        	
            return null;
        }

        // Chiffre le mot de passe avant de le stocker
        String encodedPassword = passwordEncoder.encode(password);

        // Crée un nouvel utilisateur avec le mot de passe chiffré
        User user = new User(name, email, encodedPassword);

        // Enregistre l'utilisateur dans la base de données
        return userRepository.save(user);
    }
    
    /**
     * Authentifie un utilisateur.
     *
     * @param email    Email de l'utilisateur
     * @param password Mot de passe de l'utilisateur (en clair)
     * @return L'utilisateur authentifié ou null si l'authentification échoue
     */
    public User loginUser(String email, String password) {
    	
        // Recherche l'utilisateur par email
        User user = userRepository.findByEmail(email);
        
        if (user != null) {
            // Vérifie si le mot de passe correspond (en comparant avec le mot de passe chiffré)
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Authentification réussie
                return user;
            }
        }
        // Authentification échouée
        return null;
    }
    
    /**
     * Trouve un utilisateur par email.
     *
     * @param email Email de l'utilisateur
     * @return L'utilisateur ou null s'il n'existe pas
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}