package com.example.todofamilyapi.controller.dtos.responses;

import java.util.List;

public record UsersResponseDTO(
        Long id,
        String name,
        String email,
        List<FamilyResponseDTO> family
) {
}
