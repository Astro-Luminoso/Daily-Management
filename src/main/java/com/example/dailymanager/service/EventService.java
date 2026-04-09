package com.example.dailymanager.service;

import com.example.dailymanager.dto.EventDto;
import com.example.dailymanager.dto.PostEventDto;
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

    public PostEventDto createNewEvent(PostEventDto newEvent) {

        this.eventRepository.save(new Event(
                newEvent.title(),
                newEvent.description(),
                newEvent.author(),
                encoder.encode(newEvent.password())
        ));

        return new PostEventDto(
                newEvent.title(),
                newEvent.description(),
                newEvent.author(),
                null    /* Password should not be returned */
        );
    }

    public List<EventDto> getAllEvents() {

        return eventRepository.findAll().stream()
                .map(event ->
                        new EventDto(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getAuthor(),
                                event.getUpdatedDate()))
                .toList();
    }
}
