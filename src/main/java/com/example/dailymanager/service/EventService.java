package com.example.dailymanager.service;

import com.example.dailymanager.dto.EventDto;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {

        this.eventRepository = eventRepository;
    }

     public List<EventDto> getAllEvents() {

         return eventRepository.findAll().stream()
                 .map(event -> new EventDto(event.getId(), event.getTitle(), event.getDescription(), event.getUpdatedDate()))
                 .toList();
     }
}
