package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.PostCommentRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment 관련 HTTP요쳥을 받는 컨트롤러.
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final Logger logger;

    /**
     * Comment Controller 생성자. CommentService를 주입받는다.
     *
     * @param commentService CommentService 객체, 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
        this.logger = LoggerFactory.getLogger(CommentController.class);
    }

    /**
     * 댓글 생성 API 엔드포인트. POST 요청으로 "/comments" 경로에 매핑된다.
     * 요청 본문으로 PostCommentRequestDto 객체를 받아 댓글을 생성하고, 생성된 댓글 정보를 CommentResponseDto 객체로 반환한다.
     *
     * @param reqBody 요청 바디에 포함된 댓글 생성 정보, PostCommentRequestDto 객체로 매핑된 요청 본문
     * @return 생성된 댓글 정보, CommentResponseDto 객체로 반환
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody PostCommentRequestDto reqBody) {
        logger.info("POST /comments : Create Comment for Event ID: {}", reqBody.eventId());
        CommentResponseDto resBody = commentService.saveComment(reqBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(resBody);
    }
}
