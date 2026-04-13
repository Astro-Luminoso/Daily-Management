package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

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
