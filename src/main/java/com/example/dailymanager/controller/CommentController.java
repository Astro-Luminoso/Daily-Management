package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.PostCommentRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final Logger logger;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
        this.logger = LoggerFactory.getLogger(CommentController.class);
    }

    @PostMapping
    public CommentResponseDto createComment(PostCommentRequestDto reqBody) {
        logger.info("POST: /comments - Create Comment for Event ID: {}", reqBody.eventId());
        return commentService.saveComment(reqBody);
    }
}
