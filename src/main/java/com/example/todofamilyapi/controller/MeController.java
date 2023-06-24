package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.controller.dtos.responses.UsersResponseDTO;
import com.example.todofamilyapi.controller.mappers.UsersMapper;
import com.example.todofamilyapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("v1/me")
@RestController
public class MeController {

    private final UserService userService;
    private final UsersMapper userMapper;

    @GetMapping
    public UsersResponseDTO findById(Principal principal) {
        final var user = userService.findByEmail(principal.getName());
        return user.map(userMapper::fromEntity).orElse(null);
    }
}
