package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.events.InviteDeletedEvent;
import com.example.todofamilyapi.exceptions.FamilyNotFoundException;
import com.example.todofamilyapi.exceptions.PermissionDeniedException;
import com.example.todofamilyapi.repositories.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public Family save(Family family, Principal principal) {
        userService.findByEmail(principal.getName()).ifPresent(user -> family.setUsers(List.of(user)));
        family.setFamilyOwner(principal.getName());
        return familyRepository.save(family);
    }

    public Family findById(Long id) {
        return familyRepository.findById(id).orElseThrow(() -> new FamilyNotFoundException("family not found!"));
    }

    public void deleteById(Long id, Principal pricipal) {
        if (isOwner(id, pricipal)) {
            throw new PermissionDeniedException("you are not the owner of this family!");
        }
        familyRepository.deleteById(id);
    }

    public List<Family> listAllFamily(Principal principal) {
        return familyRepository.findAllByUsers_Email(principal.getName());
    }

    public void vinculateFamily(Long userId, Long familyId, String familyCode) {
        Family family = findById(familyId);
        family.getUsers().add(userService.findById(userId));

        if (family.getFamilyCode().equals(familyCode)) {
            familyRepository.save(family);

            InviteDeletedEvent event = new InviteDeletedEvent(this, userId, familyCode);
            eventPublisher.publishEvent(event);
        } else {
            throw new FamilyNotFoundException("invalid family code!");
        }
    }

    boolean isOwner(Long familyId, Principal principal) {
        return findById(familyId).getFamilyOwner().equals(principal.getName());
    }
}
