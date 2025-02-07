package com.eventmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.model.User;
import com.eventmanager.model.UserCredentials;
import com.eventmanager.service.UserService;
import com.eventmanager.util.JwtUtil;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User userToCreate) {
    	
    	User user = userService.registerUser(userToCreate.getName(), userToCreate.getEmail(), userToCreate.getPassword());
    	
        if (user != null) {
            return new ResponseEntity<>("Utilisateur enregistré avec succès !", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("L'utilisateur avec cet email existe déjà.", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials userCredentials) {

        User user = userService.loginUser(userCredentials.getEmail(), userCredentials.getPassword());
        
        if (user != null) {
            // Génère un token JWT
            String token = jwtUtil.generateToken(userCredentials.getEmail());
            // Retourne le token avec un statut 200 OK
            return ResponseEntity.ok(token);
        } else {
            // Authentification échouée, retourne 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Email ou mot de passe incorrect.");
        }
    }
}