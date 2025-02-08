package com.eventmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanager.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	
    List<Event> findByCreatorId(Long creatorId);
    
    List<Event> findByDateAfter(LocalDate date);
}