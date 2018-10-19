package bttc.app.service.impl;

import bttc.app.model.Event;
import bttc.app.repository.EventRepository;
import bttc.app.service.EventService;
import bttc.app.util.ObjectMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EventServiceImpl  implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event addEvent(Event event) {
        return eventRepository.addEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {

        return eventRepository.updateEvent(event);
    }

    @Override
    public Event getEvent(String id){

        return eventRepository.getEvent(id);
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try {
            CompletableFuture<List<String>> allEvents = eventRepository.getAllEvents();

            for (String s : allEvents.get()) {
                events.add((Event) ObjectMappingUtil.mapChats(s,Event.class));
            }
        } catch (ExecutionException | InterruptedException e) {
           logger.error(String.format("Exception getting all event: %s",e.getMessage()));
        }
        return events;
    }


}
