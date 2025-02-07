package com.eventmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.model.Event;
import com.eventmanager.model.User;
import com.eventmanager.repository.EventRepository;

@Service
public class EventService {

	@Autowired
    private EventRepository eventRepository;
	
    public Event createEvent(String title, String description, LocalDate date, String location, User creator) {
        
    	Event event = new Event(title, date, location, description, creator);
        
    	return eventRepository.save(event);
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
    public Event updateEvent(Long eventId, String title, String description, LocalDate date, String location, User creator) {
        Optional<Event> existingEvent = eventRepository.findById(eventId);
        if (existingEvent.isPresent() && existingEvent.get().getCreator().getId().equals(creator.getId())) {
            Event event = existingEvent.get();
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(date);
            event.setLocation(location);
            return eventRepository.save(event);
        }
        return null;
    }

 // Retourner tous les événements
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}