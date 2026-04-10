package com.example.dailymanager.service;

import com.example.dailymanager.dto.*;
import com.example.dailymanager.entity.Event;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.PasswordNotMatchException;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public EventService(EventRepository eventRepository, PasswordEncoder encoder) {
        this.eventRepository = eventRepository;
        this.encoder = encoder;
    }

    private Event getAuthorizedEvent (String inputPassword, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        boolean passwordIsMatch = encoder.matches(inputPassword, event.getPassword());

        if (!passwordIsMatch)
            throw new PasswordNotMatchException();

        return event;
    }

    @Transactional
    public PostEventResponseDto createNewEvent(PostEventRequestDto newEvent) {

        eventRepository.save(new Event(
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

    public List<EventResponseDto> getEvents(String author) {

        List<Event> events = (author == null) ? eventRepository.findAllByOrderByUpdatedDateDesc()
                : eventRepository.findByAuthorOrderByUpdatedDateDesc(author);

        return events.stream()
                .map(event -> new EventResponseDto(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getAuthor(),
                        event.getUpdatedDate()))
                .toList();
    }

    @Transactional
    public EventResponseDto updateEvent(long id, UpdateEventRequestDto req) {

        Event event = getAuthorizedEvent(req.password(), id);
        event.updateEventDetail(req.title(), req.author());
        eventRepository.save(event);

        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor(),
                event.getUpdatedDate()
        );
    }

    @Transactional
    public void deleteEvent(long id, DeleteRequestDto req) {

        Event event = getAuthorizedEvent(req.password(), id);
        eventRepository.delete(event);
    }
}
