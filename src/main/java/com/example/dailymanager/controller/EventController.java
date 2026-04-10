package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.DeleteRequestDto;
import com.example.dailymanager.dto.request.PostEventRequestDto;
import com.example.dailymanager.dto.request.UpdateEventRequestDto;
import com.example.dailymanager.dto.response.EventResponseDto;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.exception.PasswordNotMatchException;
import com.example.dailymanager.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {

        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDto> retrieveEvents(
            @RequestParam(required = false) String author) {

        return eventService.getEvents(author);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> addNewEvent(
            @RequestBody PostEventRequestDto newEventDto) {
        try {
            EventResponseDto createdEvent = eventService.createNewEvent(newEventDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (InvalidValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequestDto req) {

        try {
            EventResponseDto resBody = eventService.updateEvent(id, req);
            return ResponseEntity.status(HttpStatus.OK).body(resBody);
        } catch (InvalidValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @RequestBody DeleteRequestDto req) {
        try {
            eventService.deleteEvent(id, req);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(InvalidValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (PasswordNotMatchException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
