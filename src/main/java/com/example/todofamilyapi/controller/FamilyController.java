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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/family")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final FamilyMapper familysMapper;

    @PostMapping
    public FamilyResponseDTO saveUser(@RequestBody FamilyRequestDTO familyRequestDTO) {
        final Family entity = familysMapper.toEntity(familyRequestDTO);
        return familysMapper.fromEntity(familyService.save(entity));
    }

    @GetMapping("/find/{id}")
    public FamilyResponseDTO findById(@PathVariable Long id) {
        return familysMapper.fromEntity(familyService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        familyService.deleteUserById(id);
    }

    @GetMapping("/find-all-familys")
    public List<FamilyResponseDTO> listAllFamily() {
        return familyService.listAllFamily().stream().map(familysMapper::fromEntity).toList();
    }
}
