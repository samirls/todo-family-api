package com.example.todofamilyapi.controller.dtos.responses;

public record TodoResponseDTO(
        Long id,
        String todoName,
        Boolean concluded
) {
}
