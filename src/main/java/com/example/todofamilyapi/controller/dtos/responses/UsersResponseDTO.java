package com.example.todofamilyapi.controller.dtos.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UsersResponseDTO(
        Long id,
        String name,
        String email,
        Boolean admin,
        Boolean active,
        LocalDate createdAt,
        LocalDateTime lastAccess,
        List<FamilyResponseDTO> family,
        List<InviteResponseDTO> invites
) {
}
