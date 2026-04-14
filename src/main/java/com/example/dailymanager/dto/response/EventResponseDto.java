package com.example.dailymanager.dto.response;

/**
 * 일정 정보를 응답하기 위한 DTO 클래스. 일정의 ID, 제목, 설명, 작성자, 그리고 생성 및 수정된 날짜를 포함한다.
 * @param id 일정의 고유 Id
 * @param title 일정의 제목
 * @param description 일정의 설명
 * @param author 일정의 작성자
 * @param date 일정 생성 및 수정된 날짜
 */
public record EventResponseDto(long id, String title, String description, String author, String date) {
}
