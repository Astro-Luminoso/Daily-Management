package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

/**
 * 일정 정보 수정 요청을 위한 DTO 클래스. 일정의 제목, 작성자, 그리고 비밀번호를 포함한다.
 * 일정 수정 시 제목과 작성자만 변경될 수 있으며, 비밀번호는 일정 수정 권한을 검증하는 데 사용된다.
 *
 * @param title 일정의 제목. 공백이 아니어야 하며 최대 30자까지 허용된다.
 * @param author 일정의 작성자. 공백이 아니어야 한다.
 * @param password 일정의 비밀번호. 공백이 아니어야 한다. 일정 수정 시 이 비밀번호가 유효한지 검증된다.
 */
public record UpdateEventRequestDto(
        String title,
        String author,
        String password) implements Validatable {

    @Override
    public boolean isInvalid() {
        boolean isValidTitle = !title.isBlank() && title.length() <= 30;
        boolean isValidAuthor = !author.isBlank();
        boolean isValidPassword = !password.isBlank();

        return !isValidTitle || !isValidAuthor || !isValidPassword;
    }
}
