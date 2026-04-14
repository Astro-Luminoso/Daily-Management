package com.example.dailymanager.dto.response;

/**
 * 댓글 응답을 위한 DTO 클래스. 댓글의 ID, 내용, 작성자, 댓글이 달린 일정의 ID, 그리고 댓글 작성 날짜를 포함한다.
 *
 * @param id 댓글의 고유 Id
 * @param content 댓글의 내용.
 * @param author 작성자 이름.
 * @param eventId 이벤트 고유 Id.
 * @param date 생성 및 수정된 날짜.
 */
public record CommentResponseDto(long id, String content, String author, long eventId, String date) {

}
