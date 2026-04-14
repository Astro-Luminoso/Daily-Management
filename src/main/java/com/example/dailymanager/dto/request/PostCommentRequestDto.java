package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

/**
 * 댓글 생성시 사용되는 DTO 클래스. 댓글의 내용, 작성자, 비밀번호, 그리고 댓글이 달릴 일정의 ID를 포함한다.
 *
 * @param content 댓글의 내용. 공백이 아니어야 하며 최대 100자까지 허용된다.
 * @param author 작성자 이름. 공백이 아니어야 한다.
 * @param password 비밀번호. 공백이 아니어야 한다. 댓글 수정 및 삭제 시 이 비밀번호가 사용된다.
 * @param eventId 일정 ID. 댓글이 달릴 일정의 고유 ID를 나타낸다. 이 ID는 유효한 일정에 해당해야 한다.
 */
public record PostCommentRequestDto(String content,
                                    String author,
                                    String password,
                                    long eventId) implements Validatable {

    @Override
    public boolean isInvalid() {
        boolean isValidContent = !content.isBlank() && content.length() <= 100;
        boolean isValidAuthor = !author.isBlank();
        boolean isValidPassword = !password.isBlank();

        return !isValidContent || !isValidAuthor || !isValidPassword;
    }
}
