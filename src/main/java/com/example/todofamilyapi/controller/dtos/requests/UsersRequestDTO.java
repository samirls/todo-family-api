package com.example.todofamilyapi.controller.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsersRequestDTO(
        Long id,
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String password
) {

}
