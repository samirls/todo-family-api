package com.example.todofamilyapi.controller.dtos.responses;

public record UsersResponseDTO(
        Long id,
        String name,
        String email,
        FamilyResponseDTO family
) {
}
