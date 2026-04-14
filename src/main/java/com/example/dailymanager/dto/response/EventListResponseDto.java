package com.example.dailymanager.dto.response;


import java.util.List;

/**
 * 일정 목록 응답을 위한 DTO 클래스. 여러 일정의 정보를 담은 리스트를 포함한다.
 *
 * @param eventList 일정 정보가 담긴 EventResponseDto 객체들의 리스트.
 */
public record EventListResponseDto(List<EventResponseDto> eventList) {
}
