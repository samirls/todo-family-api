package com.example.todofamilyapi.controller.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record SignupUserRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
