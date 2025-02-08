package com.eventmanager.model;

import java.time.LocalDate;

public class EventResponse {

		private Long eventId;
	    private String title;
	    private LocalDate date;
	    private String location;
	    private String description;
	    private String nameCreator;
	    private String emailCreator;
	    private Long creatorId; 
	    
	    public EventResponse(){
	    	
	    }
	    
		public EventResponse(String title, LocalDate date, String location, String description, String nameCreator, String emailCreator, Long eventId, Long creatorId) {
			this.title = title;
			this.date = date;
			this.location = location;
			this.description = description;
			this.nameCreator = nameCreator;
			this.emailCreator = emailCreator;
			this.eventId = eventId;
			this.creatorId = creatorId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getNameCreator() {
			return nameCreator;
		}

		public void setNameCreator(String nameCreator) {
			this.nameCreator = nameCreator;
		}

		public Long getEventId() {
			return eventId;
		}

		public void setEventId(Long eventId) {
			this.eventId = eventId;
		}

		public String getEmailCreator() {
			return emailCreator;
		}

		public void setEmailCreator(String emailCreator) {
			this.emailCreator = emailCreator;
		}

		public Long getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(Long creatorId) {
			this.creatorId = creatorId;
		}
}