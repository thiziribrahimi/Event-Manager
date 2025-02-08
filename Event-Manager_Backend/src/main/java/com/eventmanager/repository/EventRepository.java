package com.eventmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanager.model.Event;
import com.eventmanager.model.User;

public interface EventRepository extends JpaRepository<Event, Long> {
	
    List<Event> findByCreatorId(Long creatorId);
    
    List<Event> findByDateAfter(LocalDate date);
    
    List<Event> findByCreator(User user);
}