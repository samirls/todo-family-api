package com.example.todofamilyapi.controller.dtos.requests;

public record FamilyRequestDTO(
        Long userId,
        Long id,
        String name
) {
}
