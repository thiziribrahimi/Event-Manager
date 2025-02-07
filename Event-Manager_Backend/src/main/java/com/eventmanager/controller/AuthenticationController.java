package com.eventmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.model.User;
import com.eventmanager.service.UserService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
    @PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User userToCreate) {
    	
    	User user = userService.registerUser(userToCreate.getName(), userToCreate.getEmail(), userToCreate.getPassword());
    	
        if (user != null) {
            return new ResponseEntity<>("Utilisateur enregistré avec succès !", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("L'utilisateur avec cet email existe déjà.", HttpStatus.BAD_REQUEST);
        }
    }
}