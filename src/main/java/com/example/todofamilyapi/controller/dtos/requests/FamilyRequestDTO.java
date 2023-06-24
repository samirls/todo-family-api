package com.example.todofamilyapi.controller.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record FamilyRequestDTO(
        Long userId,
        Long id,
        @NotBlank
        String name
) {
}
