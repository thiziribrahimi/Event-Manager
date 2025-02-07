package com.eventmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.model.Event;
import com.eventmanager.model.EventResponse;
import com.eventmanager.model.User;
import com.eventmanager.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody Event event, Principal principal) {
		if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Caster principal en UsernamePasswordAuthenticationToken
        Authentication auth = (Authentication) principal;
        User creator = (User) auth.getPrincipal();

        if (creator == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        EventResponse createdEvent = eventService.createEvent(event.getTitle(), event.getDescription(), event.getDate(), event.getLocation(), creator);
        
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
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @RequestBody Event event, Principal principal) {
    	
    	Authentication auth = (Authentication) principal;
        User creator = (User) auth.getPrincipal();
        EventResponse updatedEvent = eventService.updateEvent(id, event.getTitle(), event.getDescription(), event.getDate(), event.getLocation(), creator);
        
        if (updatedEvent != null) {
            return ResponseEntity.ok(updatedEvent);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    // Retourner tous les événements
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {

        return ResponseEntity.ok(eventService.getAllEvents());
    }
}
