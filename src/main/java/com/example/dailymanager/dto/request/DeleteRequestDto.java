package com.example.dailymanager.dto.request;

import com.example.dailymanager.dto.Validatable;

public record DeleteRequestDto(String password) implements Validatable {

    @Override
    public String[] getRequiredValues() {
        return new String[] { password };
    }
}
