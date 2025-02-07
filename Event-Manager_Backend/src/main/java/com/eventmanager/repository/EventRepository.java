package com.eventmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventmanager.model.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
	
    List<Event> findByCreatorId(Long creatorId);
}