package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.controller.dtos.requests.FamilyRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.FamilyResponseDTO;
import com.example.todofamilyapi.controller.mappers.FamilyMapper;
import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.services.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/family")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyMapper familysMapper;

    @PostMapping
    public FamilyResponseDTO saveFamily(@RequestBody FamilyRequestDTO familyRequestDTO, Principal principal) {
        final Family entity = familysMapper.toEntity(familyRequestDTO);
        return familysMapper.fromEntity(familyService.save(entity, principal));
    }

    @GetMapping("{id}")
    public FamilyResponseDTO findById(@PathVariable Long id) {
        return familysMapper.fromEntity(familyService.findById(id));
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id, Principal principal) {
        familyService.deleteById(id, principal);
    }

    @GetMapping
    public List<FamilyResponseDTO> listAllFamily(Principal principal) {
        return familyService.listAllFamily(principal).stream().map(familysMapper::fromEntity).toList();
    }

    @PutMapping
    public void vinculateFamily(@RequestParam Long userId, @RequestParam Long familyId, @RequestParam String familyCode) {
        familyService.vinculateFamily(userId, familyId, familyCode);
    }
}
