package com.example.dailymanager.controller;

import com.example.dailymanager.dto.*;
import com.example.dailymanager.service.EventService;
import jakarta.validation.Valid;
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
    public EventController(EventService eventService) {

        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDto> retrieveEvents(
            @RequestParam(required = false) String author) {

        return eventService.getEvents(author);
    }

    @PostMapping
    public ResponseEntity<PostEventResponseDto> addNewEvent(
            @Valid @RequestBody PostEventRequestDto newEventDto) {

        PostEventResponseDto createdEvent = eventService.createNewEvent(newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequestDto req) {

        try {
            EventResponseDto resBody = eventService.updateEvent(id, req);
            return ResponseEntity.status(HttpStatus.OK).body(resBody);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @Valid @RequestBody DeleteRequestDto req) {
        try {
            eventService.deleteEvent(id, req);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
