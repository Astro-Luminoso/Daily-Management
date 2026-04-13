package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

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
