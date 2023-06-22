package com.example.todofamilyapi.controller.dtos.requests;

public record TodoRequestDTO(
        Long familyId,
        Long id,
        String todoName,
        Boolean concluded
) {
}
