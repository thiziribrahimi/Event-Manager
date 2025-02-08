package com.eventmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.model.Event;
import com.eventmanager.model.EventResponse;
import com.eventmanager.model.User;
import com.eventmanager.repository.EventRepository;

@Service
public class EventService {

	@Autowired
    private EventRepository eventRepository;
	
    public EventResponse createEvent(String title, String description, LocalDate date, String location, User creator) {
        
    	Event event = new Event(title, date, location, description, creator);
    	
    	Event eventCreated = eventRepository.save(event);
    	
    	return new EventResponse(eventCreated.getTitle(), eventCreated.getDate(), eventCreated.getLocation(),
    			eventCreated.getDescription(), eventCreated.getCreator().getName(),
    			eventCreated.getCreator().getEmail());
        
    }
    
 // Supprimer un événement
    public boolean deleteEvent(Long eventId, User creator) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent() && existingEvent.get().getCreator().getId().equals(creator.getId())) {
            eventRepository.delete(existingEvent.get());
            return true;
        }
        return false;
    }
    
 // Mettre à jour un événement
    public EventResponse updateEvent(Long eventId, String title, String description, LocalDate date, String location, User creator) {
        
    	Optional<Event> existingEvent = eventRepository.findById(eventId);
        
    	if (existingEvent.isPresent() && existingEvent.get().getCreator().getId().equals(creator.getId())) {
            Event event = existingEvent.get();
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(date);
            event.setLocation(location);
            
            Event eventUpdated = eventRepository.save(event);
            
            return new EventResponse(eventUpdated.getTitle(), eventUpdated.getDate(), eventUpdated.getLocation(),
        			eventUpdated.getDescription(), eventUpdated.getCreator().getName(),
        			eventUpdated.getCreator().getEmail()); 
        }
        
        return null;
    }

 // Retourner tous les événements
    public List<EventResponse> getAllEvents() {
 
    	List<Event> listEvents = eventRepository.findAll();
    	return listEvents.stream()
                		 .map(event -> new EventResponse(event.getTitle(), event.getDate(), event.getLocation(),
                				 event.getDescription(), event.getCreator().getName(),
                				 event.getCreator().getEmail()
                			 ))
                		 .collect(Collectors.toList());
    }
    
    public List<EventResponse> getUpcomingEvents() {
    	
        LocalDate today = LocalDate.now();
        List<Event> listEvents = eventRepository.findByDateAfter(today);
        
        return listEvents.stream()
        				 .map(event -> new EventResponse(event.getTitle(), event.getDate(), event.getLocation(),
        					  event.getDescription(), event.getCreator().getName(),
        					  event.getCreator().getEmail()
        					 ))
        				 .collect(Collectors.toList());
    }
    
    // Méthode pour récupérer les événements créés par un utilisateur
    public List<EventResponse> getCreatedEvents(User user) {
    	List<Event> listEvents = eventRepository.findByCreator(user);
    	
    	return listEvents.stream()
				 .map(event -> new EventResponse(event.getTitle(), event.getDate(), event.getLocation(),
					  event.getDescription(), event.getCreator().getName(),
					  event.getCreator().getEmail()
					 ))
				 .collect(Collectors.toList());
    }
}