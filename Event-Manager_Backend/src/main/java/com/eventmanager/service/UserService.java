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
}