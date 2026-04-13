package com.example.dailymanager.service;

import com.example.dailymanager.dto.request.PostCommentRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.entity.Comment;
import com.example.dailymanager.exception.CommentExceedException;
import com.example.dailymanager.exception.EventNotFoundException;
import com.example.dailymanager.exception.InvalidValueException;
import com.example.dailymanager.repository.CommentRepository;
import com.example.dailymanager.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    public CommentService(CommentRepository commentRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }

    private CommentResponseDto toCommentResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getEventId(),
                comment.getUpdatedDate()
        );
    }

    public List<CommentResponseDto> findCommentsByEventId(long eventId) {

        return commentRepository.findByEventId(eventId).stream()
                .map(this::toCommentResponseDto)
                .toList();
    }

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
                reqBody.password(),
                reqBody.eventId()));

        return toCommentResponseDto(comment);
    }
}
