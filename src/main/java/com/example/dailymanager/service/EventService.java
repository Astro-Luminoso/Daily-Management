package com.example.dailymanager.service;

import com.example.dailymanager.dto.*;
import com.example.dailymanager.entity.Event;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public EventService(EventRepository eventRepository, PasswordEncoder encoder) {
        this.eventRepository = eventRepository;
        this.encoder = encoder;
    }

    public PostEventResponseDto createNewEvent(PostEventDto newEvent) {

        this.eventRepository.save(new Event(
                newEvent.title(),
                newEvent.description(),
                newEvent.author(),
                encoder.encode(newEvent.password())
        ));

        return new PostEventResponseDto(
                newEvent.title(),
                newEvent.description(),
                newEvent.author()
        );
    }

    public List<EventDto> getEvents(String author) {

        List<Event> events = (author == null) ? eventRepository.findAllByOrderByUpdatedDateDesc()
                : eventRepository.findByAuthorOrderByUpdatedDateDesc(author);

        return events.stream()
                .map(event -> new EventDto(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getAuthor(),
                        event.getUpdatedDate()))
                .toList();
    }

    public EventDto updateEvent(long id, UpdateEventRequestDto req) throws IllegalAccessException {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event Not Found."));

        boolean passwordIsMatch = encoder.matches(req.password(), event.getPassword());

        if (!passwordIsMatch)
            throw new IllegalAccessException("InvalidPassword");

        event.updateEventDetail(req.title(), req.author());
        eventRepository.save(event);

        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor(),
                event.getUpdatedDate()
        );
    }

    public void deleteEvent(long id, DeleteRequestDto req) throws IllegalAccessException {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event Not Found."));

        boolean passwordIsMatch = encoder.matches(req.password(), event.getPassword());

        if (!passwordIsMatch)
            throw new IllegalAccessException("InvalidPassword");

        eventRepository.delete(event);
    }
}
