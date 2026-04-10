package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

public record UpdateEventRequestDto(
        String title,
        String author,
        String password) implements Validatable {

    @Override
    public String[] getRequiredValues() {
        return new String[] {title, author, password};
    }
}
