package com.example.todofamilyapi.controller.dtos.responses;

import java.util.List;

public record FamilyResponseDTO(
        Long id,
        String name,
        List<TodoResponseDTO> todos
) {
}
