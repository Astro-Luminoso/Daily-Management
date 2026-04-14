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

import java.util.List;

/**
 * 일정 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
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

    /**
     * HTTP응답에 사용할 EventResponseDTO를 Event 엔티티로부터 변환하는 메서드
     *
     * @param event 응답으로 반환하고자 하는 event 엔티티 객체
     * @return 반환하고자 하는 event 엔티티 객체로부터 변환된 EventResponseDto 객체
     */
    private EventResponseDto toEventResponseDto(Event event) {
        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor(),
                event.getUpdatedDate()
        );
    }

    /**
     * 일정 호출시 비밀번호 검증이 필요한 경우, 비밀번호 검증 수행을 한 후 일정 인티티를 반환하는 메서드
     *
     * @param inputPassword 클라이언트로부터 전달받은 비밀번호
     * @param eventId 조회하고자하는 이벤트의 고유 Id
     * @return 비밀번호 검증이 완료된 일정 엔티티 객체
     */
    private Event getAuthorizedEvent(String inputPassword, long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);

        if (!event.isPasswordMatch(inputPassword, encoder)) {
            throw new PasswordNotMatchException();
        }

        return event;
    }

    /**
     * reqBody의 유효성 검사를 수행한 후 새로운 일정을 생성하는 메서드.
     *
     * @param reqBody PostEventRequestDto로 레핑된 생성하고자 하는 일정의 정보가 포함된 요청 본문
     * @return 생성된 일정 정보가 포함된 응답 객체, EventResponseDto 객체로 래핑되어 반환
     */
    @Transactional
    public EventResponseDto createNewEvent(@Nonnull PostEventRequestDto reqBody) {
        if (reqBody.isInvalid()) {
            throw new InvalidValueException();
        }

        Event event = eventRepository.save(new Event(
                reqBody.title(),
                reqBody.description(),
                reqBody.author(),
                encoder.encode(reqBody.password())));

        return toEventResponseDto(event);
    }

    /**
     * 일정 목록을 조회하는 메서드.
     * author 파라미터가 null인 경우 전체 일정 목록을 반환하고, author 파라미터가 null이 아닌 경우 해당 작성자의 일정 목록을 반환한다.
     *
     * @param author 선택적으로 전달받는 작성자 이름. null인 경우 전체 일정 목록을 조회한다.
     * @return 생성된 일정 정보가 포함된 응답 객체의 리스트, EventResponseDto 객체로 래핑되어 리스트 형태로 반환
     */
    public EventListResponseDto getEvents(String author) {
        List<Event> events = (author == null) ? eventRepository.findAllByOrderByUpdatedDateDesc()
                : eventRepository.findByAuthorOrderByUpdatedDateDesc(author);

        return new EventListResponseDto(
                events.stream().map(this::toEventResponseDto).toList()
        );
    }

    /**
     * 일정 단건 조회 메서드. 일정의 상세 정보와 해당 일정에 포함된 댓글 목록을 함께 반환한다.
     *
     * @param id 요청받은 일정의 id
     * @return 생성된 일정 정보가 포함된 응답 객체, EventResponseDto 객체로 래핑되어 반환
     */
    public EventDetailResponseDto getEventById(@PathVariable long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);

        EventResponseDto eventDto = toEventResponseDto(event);
        List<CommentResponseDto> commentsDto = commentService.findCommentsByEventId(id);

        return new EventDetailResponseDto(eventDto, commentsDto);
    }


    /**
     * 일정 수정 메서드. 일정의 제목과 작성자를 수정할 수 있다. 일정 수정시 비밀번호 검증이 필요하다.
     *
     * @param id 요청받은 일정의 id
     * @param reqBody 수정하고자 하는 일정의 제목과 작성자 정보가 포함된 요청 본문, UpdateEventRequestDto 객체로 매핑된 요청 본문
     * @return 생성된 일정 정보가 포함된 응답 객체, EventResponseDto 객체로 래핑되어 반환
     */
    @Transactional
    public EventResponseDto updateEvent(
            long id,
            @Nonnull UpdateEventRequestDto reqBody
    ) {
        if (reqBody.isInvalid()) {
            throw new InvalidValueException();
        }

        Event event = getAuthorizedEvent(reqBody.password(), id);
        event.updateEventDetail(reqBody.title(), reqBody.author());

        return toEventResponseDto(event);
    }

    /**
     * 일정 수정 메서드. 일정 삭제시 비밀번호 검증이 필요하다.
     *
     * @param id 요청 받은 일정의 id
     * @param reqBody 삭제하고자 하는 일정의 비밀번호가 포함된 요청 본문, DeleteRequestDto 객체로 매핑된 요청 본문
     */
    @Transactional
    public void deleteEvent(
            long id,
            @Nonnull DeleteRequestDto reqBody
    ) {
        if (reqBody.isInvalid()) {
            throw new InvalidValueException();
        }

        Event event = getAuthorizedEvent(reqBody.password(), id);
        eventRepository.delete(event);
    }
}
