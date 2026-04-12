package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

public record PostCommentRequestDto(String content,
                                    String author,
                                    String password,
                                    long eventId) implements Validatable {

    @Override
    public String[] getRequiredValues() {
        return new String[] {content(), author(), password()};
    }
}
