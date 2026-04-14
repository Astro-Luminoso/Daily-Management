package com.example.dailymanager.dto.response;

import java.util.List;

/**
 * 이벤트 상세 정보를 반환하기 위한 DTO 클래스. 일정의 상세 정보와 해당 이벤트에 달린 댓글 목록을 포함한다.
 *
 * @param eventDto 반환하고자 하는 일정의 상세 정보를 담은 EventResponseDto 객체.
 * @param commentDtoList 일정의 댓글 목록을 담은 CommentResponseDto 객체들의 리스트.
 */
public record EventDetailResponseDto(EventResponseDto eventDto, List<CommentResponseDto> commentDtoList) {
}
