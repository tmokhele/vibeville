package bttc.app.service;

import bttc.app.model.Event;

import java.util.List;

public interface EventService {

    Event addEvent(Event event);
    Event updateEvent(Event event);
    Event getEvent(String id);
    List<Event> getAllEvents();
}
