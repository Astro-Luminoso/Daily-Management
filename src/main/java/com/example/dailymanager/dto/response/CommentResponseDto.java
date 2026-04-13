package com.example.dailymanager.dto.response;

public record CommentResponseDto(long id, String content, String author, long eventId, String date) {

}
