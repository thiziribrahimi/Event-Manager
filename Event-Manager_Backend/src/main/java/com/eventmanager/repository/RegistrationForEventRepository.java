package com.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanager.model.Event;
import com.eventmanager.model.RegistrationForEvent;
import com.eventmanager.model.User;

public interface RegistrationForEventRepository extends JpaRepository<RegistrationForEvent, Long> {
	
    boolean existsByEventAndUser(Event event, User user);
    
    void deleteByEventAndUser(Event event, User user);
}