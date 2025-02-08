package com.eventmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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
import com.eventmanager.model.RegistrationRequest;
import com.eventmanager.model.User;
import com.eventmanager.service.EventService;
import com.eventmanager.service.RegistrationForEventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private RegistrationForEventService registrationForEventService;
	
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

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {

        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
 // Endpoint pour s'inscrire à un événement
    @PostMapping("/register")
    public ResponseEntity<?> registerForEvent(@RequestBody RegistrationRequest request, Principal principal) {
        
    	if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Caster principal en UsernamePasswordAuthenticationToken
        Authentication auth = (Authentication) principal;
        User curentUser = (User) auth.getPrincipal();

        if (curentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    	if (registrationForEventService.registerForEvent(request.getEventId(), curentUser)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Endpoint pour se désinscrire d'un événement
    @Transactional
    @PostMapping("/unregister")
    public ResponseEntity<?> unregisterForEvent(@RequestBody RegistrationRequest request, Principal principal) {
        
    	if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Caster principal en UsernamePasswordAuthenticationToken
        Authentication auth = (Authentication) principal;
        User curentUser = (User) auth.getPrincipal();

        if (curentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    	if (registrationForEventService.unregisterForEvent(request.getEventId(), curentUser)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    // Endpoint pour récupérer les événements à venir
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
    	
    	return ResponseEntity.ok(eventService.getUpcomingEvents());	
    }
}