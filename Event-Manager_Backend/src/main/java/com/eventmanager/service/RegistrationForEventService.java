package com.eventmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.model.Event;
import com.eventmanager.model.RegistrationForEvent;
import com.eventmanager.model.User;
import com.eventmanager.repository.EventRepository;
import com.eventmanager.repository.RegistrationForEventRepository;

@Service
public class RegistrationForEventService {

	@Autowired
    private RegistrationForEventRepository registrationForEventRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	// Méthode pour s'inscrire à un événement
    public boolean registerForEvent(Long eventId, User user) {
    	
        Event event = eventRepository.findById(eventId).orElse(null);
        
        if (event == null || registrationForEventRepository.existsByEventAndUser(event, user)) {
        	
            return false;
        }
        
        RegistrationForEvent registration = new RegistrationForEvent(event, user);
        registrationForEventRepository.save(registration);
        
        return true;
    }

    // Méthode pour se désinscrire d'un événement
    public boolean unregisterForEvent(Long eventId, User user) {
    	
        Event event = eventRepository.findById(eventId).orElse(null);
        
        if (event == null) {
            return false;
        }
        
        registrationForEventRepository.deleteByEventAndUser(event, user);
        
        return true;
    }
}
