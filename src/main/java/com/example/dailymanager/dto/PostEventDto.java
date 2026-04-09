package com.example.dailymanager.dto;

import jakarta.validation.constraints.NotBlank;

public record PostEventDto(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String author,
        @NotBlank String password){}
