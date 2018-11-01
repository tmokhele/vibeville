package bttc.app.controller;

import bttc.app.model.Event;
import bttc.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping()
    public
    Event addEvent(@RequestBody Event event) {

        return eventService.addEvent(event);
    }

    @PutMapping()
    public Event updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @GetMapping("/{eventId}/detail")
    public Event getEvent(@PathVariable String eventId) {
        return eventService.getEvent(eventId);

    }

    @GetMapping
    public List<Event> getAllEvents() {

        return eventService.getAllEvents();
    }
}
