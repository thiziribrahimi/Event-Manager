package com.eventmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.model.Event;
import com.eventmanager.model.EventResponse;
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
    
    public List<EventResponse> getRegisteredEvents(User user) {
    	List<RegistrationForEvent> listRegistrationForEvent = registrationForEventRepository.findByUser(user);
    	
    	List<Event> listEvents = listRegistrationForEvent
    								.stream()
    								.map(RegistrationForEvent::getEvent)
    								.collect(Collectors.toList());
    	
    	return listEvents.stream()
				 .map(event -> new EventResponse(event.getTitle(), event.getDate(), event.getLocation(),
					  event.getDescription(), event.getCreator().getName(),
					  event.getCreator().getEmail(), event.getId()
					 ))
				 .collect(Collectors.toList());
    }
}
