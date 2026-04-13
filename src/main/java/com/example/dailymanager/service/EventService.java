package com.example.dailymanager.service;

import com.example.dailymanager.dto.request.DeleteRequestDto;
import com.example.dailymanager.dto.request.PostEventRequestDto;
import com.example.dailymanager.dto.request.UpdateEventRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.dto.response.EventDetailResponseDto;
import com.example.dailymanager.dto.response.EventListResponseDto;
import com.example.dailymanager.dto.response.EventResponseDto;
import com.example.dailymanager.entity.Event;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.exception.PasswordNotMatchException;
import com.example.dailymanager.repository.EventRepository;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final CommentService commentService;
    private final PasswordEncoder encoder;

    public EventService(EventRepository eventRepository,
                        CommentService commentService,
                        PasswordEncoder encoder) {
        this.eventRepository = eventRepository;
        this.commentService = commentService;
        this.encoder = encoder;
    }

    private EventResponseDto toEventResponseDto(Event event) {
        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor(),
                event.getUpdatedDate()
        );
    }

    private boolean isNullOrBlank(String[] values) {
        return Arrays.stream(values).anyMatch(value -> value == null || value.isBlank());
    }

    private Event getAuthorizedEvent(String inputPassword, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        if (!event.isPasswordMatch(inputPassword, encoder)) {
            throw new PasswordNotMatchException();
        }

        return event;
    }

    @Transactional
    public EventResponseDto createNewEvent(@Nonnull PostEventRequestDto reqBody) {
        if (isNullOrBlank(reqBody.getRequiredValues())) {
            throw new InvalidValueException();
        }

        Event event = eventRepository.save(new Event(
                reqBody.title(),
                reqBody.description(),
                reqBody.author(),
                encoder.encode(reqBody.password())));

        return toEventResponseDto(event);
    }

    public EventListResponseDto getEvents(String author) {
        List<Event> events = (author == null) ? eventRepository.findAllByOrderByUpdatedDateDesc()
                : eventRepository.findByAuthorOrderByUpdatedDateDesc(author);

        return new EventListResponseDto(
                events.stream().map(this::toEventResponseDto).toList()
        );
    }

    public EventDetailResponseDto getEventById(@PathVariable long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);

        EventResponseDto eventDto = toEventResponseDto(event);
        List<CommentResponseDto> commentsDto = commentService.findCommentsByEventId(id);

        return new EventDetailResponseDto(eventDto, commentsDto);
    }


    @Transactional
    public EventResponseDto updateEvent(
            long id,
            @Nonnull UpdateEventRequestDto reqBody
    ) {
        if (isNullOrBlank(reqBody.getRequiredValues())) {
            throw new InvalidValueException();
        }

        Event event = getAuthorizedEvent(reqBody.password(), id);
        event.updateEventDetail(reqBody.title(), reqBody.author());

        return toEventResponseDto(event);
    }

    @Transactional
    public void deleteEvent(
            long id,
            @Nonnull DeleteRequestDto reqBody
    ) {
        if (isNullOrBlank(reqBody.getRequiredValues())) {
            throw new InvalidValueException();
        }

        Event event = getAuthorizedEvent(reqBody.password(), id);
        eventRepository.delete(event);
    }
}
