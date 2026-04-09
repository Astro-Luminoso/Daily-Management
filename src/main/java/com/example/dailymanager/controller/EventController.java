package com.example.dailymanager.controller;

import com.example.dailymanager.dto.EventDto;
import com.example.dailymanager.dto.PostEventDto;
import com.example.dailymanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController (EventService eventService) {

        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDto> retrieveAllEvents() {

        return eventService.getAllEvents();
    }

    @PostMapping
    public ResponseEntity<PostEventDto> addNewEvent(@RequestBody PostEventDto newEventDto) {

        PostEventDto createdEvent = eventService.createNewEvent(newEventDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }
}
