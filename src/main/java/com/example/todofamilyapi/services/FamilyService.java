package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.exceptions.FamilyNotFoundException;
import com.example.todofamilyapi.repositories.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;

    public Family save(Family family) {
        return familyRepository.save(family);
    }

    public Family findById(Long id) {
        return familyRepository.findById(id).orElseThrow(() -> new FamilyNotFoundException("family not found!"));
    }

    public void deleteUserById(Long id) {
        familyRepository.deleteById(id);
    }

    public List<Family> listAllFamily() {
        return familyRepository.findAll();
    }

    public void vinculateFamily(Long userId, Long familyId) {
        Family family = findById(familyId);
        family.getUsers().add(userService.findById(userId));
        familyRepository.save(family);
    }
}
