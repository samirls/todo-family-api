package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.controller.dtos.responses.InviteResponseDTO;
import com.example.todofamilyapi.controller.mappers.InviteMapper;
import com.example.todofamilyapi.services.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;
    private final InviteMapper inviteMapper;

    @PostMapping
    public void invite(@RequestParam String email, @RequestParam Long familyId, Principal principal) {
        inviteService.createInvite(email, familyId, principal);
    }

    @GetMapping
    public List<InviteResponseDTO> findAll(Principal principal) {
        return inviteService.findByEmail(principal.getName()).stream().map(inviteMapper::fromEntity).toList();
    }
}
