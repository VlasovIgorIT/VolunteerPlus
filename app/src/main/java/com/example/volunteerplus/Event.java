package com.example.volunteerplus;

public class Event {
    public String id, nameEvent, descriptionEvent,numberParticipants ;

    public Event() {
    }

    public Event(String id, String pNameEvent, String pDescriptionEvent, String pNumberParticipants) {
        this.id = id;
        nameEvent = pNameEvent;
        descriptionEvent = pDescriptionEvent;
        numberParticipants = pNumberParticipants;
    }
}