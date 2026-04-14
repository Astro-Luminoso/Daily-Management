package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

/**
 * 일정 생성시 사용되는 DTO 클래스. 일정의 제목, 설명, 작성자, 그리고 비밀번호를 포함한다.
 *
 * @param title 일정의 제목. 공백이 아니어야 하며 최대 30자까지 허용된다.
 * @param description 일정의 설명. 공백이 아니어야 하며 최대 200자까지 허용된다.
 * @param author 작성자. 공백이 아니어야 한다.
 * @param password 일정의 비밀번호. 공백이 아니어야 한다. 일정 수정 및 삭제 시 이 비밀번호가 사용된다.
 */
public record PostEventRequestDto(
        String title,
        String description,
        String author,
        String password) implements Validatable {

    @Override
    public boolean isInvalid() {
        boolean isValidTitle = !title.isBlank() && title.length() <= 30;
        boolean isValidDescription = !description.isBlank() && description.length() <= 200;
        boolean isValidAuthor = !author.isBlank();
        boolean isValidPassword = !password.isBlank();

        return !isValidTitle || !isValidDescription || !isValidAuthor || !isValidPassword;
    }
}
