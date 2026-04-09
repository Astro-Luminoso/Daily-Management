package com.example.dailymanager.controller;

import com.example.dailymanager.dto.EventDto;
import com.example.dailymanager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
