package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

public record PostEventRequestDto(
        String title,
        String description,
        String author,
        String password) implements Validatable {

    @Override
    public String[] getRequiredValues() {
        return new String[] {title, description, author, password};
    }
}
