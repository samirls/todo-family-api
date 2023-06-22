package com.example.todofamilyapi.controller.dtos.requests;

public record UsersRequestDTO(
        Long id,
        String name,
        String email,
        String password
) {

}
