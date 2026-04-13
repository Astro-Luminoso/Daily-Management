package com.example.dailymanager.dto.response;

import java.util.List;

public record EventDetailResponseDto(EventResponseDto eventDto, List<CommentResponseDto> commentDtoList) {
}
