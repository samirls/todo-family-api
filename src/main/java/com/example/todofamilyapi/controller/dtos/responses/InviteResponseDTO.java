package com.example.todofamilyapi.controller.dtos.responses;

public record InviteResponseDTO(
        Long id,
        String email,
        Long idFamily,
        String inviteCode,
        String invitedName,
        String invitedFamilyName
) {
}
