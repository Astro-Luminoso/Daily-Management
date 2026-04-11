package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.DeleteRequestDto;
import com.example.dailymanager.dto.request.PostEventRequestDto;
import com.example.dailymanager.dto.request.UpdateEventRequestDto;
import com.example.dailymanager.dto.response.EventResponseDto;
import com.example.dailymanager.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final Logger logger;

    public EventController(EventService eventService) {
        this.eventService = eventService;
        this.logger = LoggerFactory.getLogger(EventController.class);
    }

    @GetMapping
    public List<EventResponseDto> retrieveEvents(
            @RequestParam(required = false) String author) {
        logger.info("GET: /events - retrieve events with author filter: {}", author);
        return eventService.getEvents(author);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> addNewEvent(
            @RequestBody PostEventRequestDto newEventDto) {
        logger.info("POST: /events - create new event");
        EventResponseDto createdEvent = eventService.createNewEvent(newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequestDto req) {
        logger.info("PATCH: /events/{} - update event", id);
        EventResponseDto resBody = eventService.updateEvent(id, req);
        return ResponseEntity.status(HttpStatus.OK).body(resBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @RequestBody DeleteRequestDto req) {
        logger.info("DELETE: /events/{} - delete event", id);
        eventService.deleteEvent(id, req);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
