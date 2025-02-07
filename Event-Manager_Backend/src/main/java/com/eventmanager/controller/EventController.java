package com.eventmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.eventmanager.model.Event;
import com.eventmanager.model.User;
import com.eventmanager.service.EventService;
import com.eventmanager.service.UserService;



@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, Principal principal) {
		if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Caster principal en UsernamePasswordAuthenticationToken
        Authentication auth = (Authentication) principal;
        User creator = (User) auth.getPrincipal();

        if (creator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        Event createdEvent = eventService.createEvent(event.getTitle(), event.getDescription(), event.getDate(), event.getLocation(), creator);
        
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Caster principal en UsernamePasswordAuthenticationToken
        Authentication auth = (Authentication) principal;
        User creator = (User) auth.getPrincipal();

        if (creator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (eventService.deleteEvent(id, creator)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
	
	// Mettre à jour un événement
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event, Principal principal) {
    	Authentication auth = (Authentication) principal;
        User creator = (User) auth.getPrincipal();
        Event updatedEvent = eventService.updateEvent(id, event.getTitle(), event.getDescription(), event.getDate(), event.getLocation(), creator);
        if (updatedEvent != null) {
            return ResponseEntity.ok(updatedEvent);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    // Retourner tous les événements
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
}
