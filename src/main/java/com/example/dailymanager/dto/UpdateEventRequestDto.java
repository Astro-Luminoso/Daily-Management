package com.example.dailymanager.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEventRequestDto(
        String title,
        String author,
        @NotBlank String password) {
}
