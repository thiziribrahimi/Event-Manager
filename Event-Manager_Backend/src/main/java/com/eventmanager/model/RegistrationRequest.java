package com.eventmanager.model;

public class RegistrationRequest {
    
	private Long eventId;

    public RegistrationRequest() {}

    public RegistrationRequest(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}