package com.example.dailymanager.service;

import com.example.dailymanager.dto.request.DeleteRequestDto;
import com.example.dailymanager.dto.request.PostEventRequestDto;
import com.example.dailymanager.dto.request.UpdateEventRequestDto;
import com.example.dailymanager.dto.response.EventResponseDto;
import com.example.dailymanager.entity.Event;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.exception.PasswordNotMatchException;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final PasswordEncoder encoder;

    public EventService(EventRepository eventRepository, PasswordEncoder encoder) {
        this.eventRepository = eventRepository;
        this.encoder = encoder;
    }

    private boolean isNullOrBlank(String[] values) {

        return Arrays.stream(values).anyMatch(value -> Objects.isNull(value) || value.isBlank());
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
    public EventResponseDto createNewEvent(PostEventRequestDto reqBody) {
        if (isNullOrBlank(reqBody.getRequiredValues()))
            throw new InvalidValueException();

        eventRepository.save(new Event(
                reqBody.title(),
                reqBody.description(),
                reqBody.author(),
                encoder.encode(reqBody.password())
        ));

        Event event = eventRepository.findTopByOrderByIdDesc()
                .orElseThrow(EventNotFoundException::new);

        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor(),
                event.getUpdatedDate()
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
    public EventResponseDto updateEvent(long id, UpdateEventRequestDto reqBody) {
        if (isNullOrBlank(reqBody.getRequiredValues()))
            throw new InvalidValueException();

        Event event = getAuthorizedEvent(reqBody.password(), id);
        event.updateEventDetail(reqBody.title(), reqBody.author());
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
    public void deleteEvent(long id, DeleteRequestDto reqBody) {
        if (isNullOrBlank(reqBody.getRequiredValues()))
            throw new InvalidValueException();

        Event event = getAuthorizedEvent(reqBody.password(), id);
        eventRepository.delete(event);
    }
}
