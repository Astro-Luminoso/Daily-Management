package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

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
