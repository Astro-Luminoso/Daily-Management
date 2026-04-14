package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.DeleteRequestDto;
import com.example.dailymanager.dto.request.PostEventRequestDto;
import com.example.dailymanager.dto.request.UpdateEventRequestDto;
import com.example.dailymanager.dto.response.EventDetailResponseDto;
import com.example.dailymanager.dto.response.EventListResponseDto;
import com.example.dailymanager.dto.response.EventResponseDto;
import com.example.dailymanager.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 일정 관련  HTTP 요청을 받는 컨트롤러
 */
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final Logger logger;

    /**
     * EventController 생성자. EventService를 주입받는다.
     *
     * @param eventService 일정관련 비즈니스 로직을 처리하는 서비스 클래스인 EventService 객체
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
        this.logger = LoggerFactory.getLogger(EventController.class);
    }

    /**
     * 일정 목록 조회 API 엔드포인트. GET 요청으로 "/events" 경로에 매핑된다.
     * 선택적으로 "author" 쿼리 파라미터를 받아 해당 작성자에 대한 일정만 필터링하여 응답 바디를 반환할 수 있다.
     * 반환되는 일정 목록은 EventListResponseDto 객체로 래핑되어 클라이언트에게 반환된다.
     *
     * @param author 일정 작성자에 대한 선택적 필터링을 위한 쿼리 파라미터.
     * @return 일정 목록이 포함된 응답 객체, EventListResponseDto 객체로 래핑되어 반환
     */
    @GetMapping
    public ResponseEntity<EventListResponseDto> retrieveEvents(
            @RequestParam(required = false) String author
    ) {
        logger.info("GET /events : retrieve events with author filter: {}", author);
        EventListResponseDto resBody = eventService.getEvents(author);
        return ResponseEntity.status(HttpStatus.OK).body(resBody);
    }

    /**
     * 일정 상세 및 일정에 포함된 댓글들을 조회하는 API 엔드포인트. GET 요청으로 "/events/{id}" 경로에 매핑된다.
     * 경로 변수로 일정 ID를 받아 해당 일정의 상세 정보를 EventDetailResponseDto 객체로 반환한다.
     *
     * @param id 조회하고자 하는 일정의 고유 Id를 나타내는 경로 변수
     * @return 일정 정보와 일정에 포함된 댓글 목록이 포함된 응답 객체, EventDetailResponseDto 객체로 래핑되어 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDetailResponseDto> retrieveEventDetail(
            @PathVariable long id
    ) {
        logger.info("GET /events/{} : retrieve event detail", id);
        EventDetailResponseDto eventDetail = eventService.getEventById(id);
        return ResponseEntity.status(HttpStatus.OK).body(eventDetail);
    }

    /**
     * 일정 생성 API 엔드포인트. POST 요청으로 "/events" 경로에 매핑된다.
     * 요청 본문으로 PostEventRequestDto 객체를 받아 새로운 일정을 생성하고, 생성된 일정의 정보를 EventResponseDto 객체로 반환한다.
     *
     * @param newEventDto 생성하고자 하는 일정의 정보가 포함된 요청 본문, PostEventRequestDto 객체로 매핑된 요청 본문
     * @return 생성된 일정 정보가 포함된 응답 객체, EventResponseDto 객체로 래핑되어 반환
     */
    @PostMapping
    public ResponseEntity<EventResponseDto> addNewEvent(
            @RequestBody PostEventRequestDto newEventDto
    ) {
        logger.info("POST /events : create new event");
        EventResponseDto createdEvent = eventService.createNewEvent(newEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    /**
     * 일정의 작성자와 일정 제목을 수정하는 API 엔드포인트. PATCH 요청으로 "/events/{id}" 경로에 매핑된다.
     * 경로 변수로 일정 ID를 받아 해당 일정의 작성자와 제목을 수정한다.
     * 요청 본문으로 UpdateEventRequestDto 객체를 받아 수정할 작성자와 제목 정보를 전달받으며
     * 수정된 일정의 정보를 EventResponseDto 객체로 반환한다.
     *
     * @param id 수정하고자 하는 일정의 고유 Id를 나타내는 경로 변수
     * @param req 수정하고자 하는 일정의 제목과 작성자 정보가 포함된 요청 본문, UpdateEventRequestDto 객체로 매핑된 요청 본문
     * @return 수정된 일정 정보가 포함된 응답 객체, EventResponseDto 객체로 래핑되어 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequestDto req
    ) {
        logger.info("PATCH /events/{} : update event", id);
        EventResponseDto resBody = eventService.updateEvent(id, req);
        return ResponseEntity.status(HttpStatus.OK).body(resBody);
    }

    /**
     * 일정 삭제 API 엔드포인트. DELETE 요청으로 "/events/{id}" 경로에 매핑된다.
     * 경로 변수로 일정 ID를 받아 해당 일정을 삭제한다.
     *
     * @param id 삭제하고자 하는 일정의 고유 Id를 나타내는 경로 변수
     * @param req 삭제하고자 하는 일정의 정보가 포함된 요청 본문, DeleteRequestDto 객체로 매핑된 요청 본문
     * @return 응답 본문이 없는 HTTP 응답 객체
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id,
            @RequestBody DeleteRequestDto req
    ) {
        logger.info("DELETE /events/{} : delete event", id);
        eventService.deleteEvent(id, req);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
