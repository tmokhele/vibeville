package bttc.app.service.impl;

import bttc.app.model.Event;
import bttc.app.repository.EventRepository;
import bttc.app.service.EventService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event addEvent(Event event) {
        return eventRepository.addEvent(event);
    }

    @Override
    public Event updateEvent(List<Event> event) {
        for (Event e : event) {

            eventRepository.updateEvent(e);

        }
        return null;
    }

    @Override
    public Event getEvent(String id) {

        return eventRepository.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        Gson g = new Gson();
        try {
            Map<String, String> allEvents = eventRepository.getAllEvents();
            for (Map.Entry<String, String> entry : allEvents.entrySet()) {
                Event event = g.fromJson(entry.getValue(), Event.class);
                event.setId(entry.getKey());
                events.add(event);
            }
        } catch (Exception e) {
            logger.error(String.format("Exception getting all event: %s", e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return events;
    }


}
