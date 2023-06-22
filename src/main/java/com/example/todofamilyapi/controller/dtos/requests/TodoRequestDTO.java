package com.example.todofamilyapi.controller.dtos.requests;

public record TodoRequestDTO(
        Long id,
        String todoName,
        Boolean concluded
) {
}
