package com.example.dailymanager.service;

import com.example.dailymanager.dto.request.PostCommentRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.entity.Comment;
import com.example.dailymanager.exception.CommentExceedException;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.repository.CommentRepository;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder encoder;

    public CommentService(
            CommentRepository commentRepository,
            EventRepository eventRepository,
            PasswordEncoder encoder) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
        this.encoder = encoder;
    }

    /**
     * HTTP응답에 사용할 CommentResponseDto를 Comment 엔티티로부터 변환하는 메서드
     *
     * @param comment 응답으로 반환하고자 하는 comment 엔티티 객체
     * @return 반환하고자 하는 comment 엔티티 객체로부터 변환된 CommentResponseDto 객체
     */
    private CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getEventId(),
                comment.getUpdatedDate()
        );
    }

    /**
     * 일정 ID에 해당하는 댓글 목록을 반환하는 메서드.
     * Comment 엔티티 객체들을 CommentResponseDto 객체로 변환하여 반환한다.
     * <h5> ::: 중요사항 ::: </h5>
     * 이 메서드는 일정 단건 조회시 사용되는 메서드로 사전에 일정 id에 대한 검사를 마쳤음을 가정하므로
     * 현재 이 메서드에서는 일정 Id에 대한 유효성 검사를 수행하지 않는다.
     * 따라서 메서드 호출 전 일정 id에 대한 유효성을 검사해야 한다.
     *
     * @param eventId 일정 Id.
     * @return 리스트 형태로 반환되는 댓글 정보, CommentResponseDto 객체로 변환된 댓글 정보의 리스트
     */
    public List<CommentResponseDto> findCommentsByEventId(long eventId) {

        return commentRepository.findByEventId(eventId).stream()
                .map(this::toCommentResponseDto)
                .toList();
    }

    /**
     * 댓글을 저장하는 메서드.
     * 댓글 저장 전 댓글 내용의 유효성 검사, 댓글이 달릴 일정의 존재 여부 검사, 해당 일정에 달린 댓글 수가 10개를 초과하는지 검사한다.
     *
     * @param reqBody 요청 본문에 포함된 댓글 생성 정보, PostCommentRequestDto 객체로 매핑된 요청 본문
     * @return 생성된 댓글 정보, CommentResponseDto 객체로 반환
     */
    @Transactional
    public CommentResponseDto saveComment(PostCommentRequestDto reqBody) {
        if(reqBody.isInvalid()) {
            throw new InvalidValueException();
        }
        if(!eventRepository.existsById(reqBody.eventId())) {
            throw new EventNotFoundException();
        }
        if (commentRepository.countByEventId(reqBody.eventId()) >= 10) {
            throw new CommentExceedException();
        }

        Comment comment = commentRepository.save(new Comment(
                reqBody.content(),
                reqBody.author(),
                encoder.encode(reqBody.password()),
                reqBody.eventId()));

        return toCommentResponseDto(comment);
    }
}
