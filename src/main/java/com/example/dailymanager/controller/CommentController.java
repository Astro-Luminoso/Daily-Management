package com.example.dailymanager.controller;

import com.example.dailymanager.dto.request.PostCommentRequestDto;
import com.example.dailymanager.dto.response.CommentResponseDto;
import com.example.dailymanager.service.CommentService;/
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentResponseDto createComment(PostCommentRequestDto reqBody) {
        return commentService.saveComment(reqBody);
    }
}
