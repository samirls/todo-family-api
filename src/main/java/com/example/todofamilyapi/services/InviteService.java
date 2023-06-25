package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Invite;
import com.example.todofamilyapi.events.InviteDeletedEvent;
import com.example.todofamilyapi.exceptions.UserNotFoundException;
import com.example.todofamilyapi.repositories.InviteRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final UserService userService;
    private final FamilyService familyService;
    private final InviteRepositoryRepository inviteRepositoryRepository;

    //TODO tarefa de casa para o Samir
    //SOMENTE OWNER DA FAMILIA PODE CONVIDAR
    //VERIFICAR SE ELE JA FAZ PARTE DA FAMILIA
    //VERIFICAR SE ELE JA FOI CONVIDADO OU ESTÁ COM UM CONVITE PENDENTE

    public Invite save(final String email, final Long familyId, Principal principal) {
        final var userWhoInvited = userService.findByEmail(principal.getName());
        final var invitedUser = userService.findByEmail(email);
        final var family = familyService.findById(familyId);

        if (Boolean.TRUE.equals(userService.existsByEmail(email))) {
            Invite invite = new Invite();
            invitedUser.ifPresent(invite::setUsers);
            userWhoInvited.ifPresent(users -> invite.setEmail(users.getEmail()));
            userWhoInvited.ifPresent(users -> invite.setInvitedName(users.getName()));
            invite.setIdFamily(familyId);
            invite.setInviteCode(family.getFamilyCode());
            invite.setInvitedFamilyName(family.getName());
            invite.setPending(true);
            return inviteRepositoryRepository.save(invite);
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    public List<Invite> findByEmail(final String name) {
        return inviteRepositoryRepository.findByUsers_Email(name);
    }

    @EventListener
    public void handleInviteDeletedEvent(InviteDeletedEvent event) {
        final Optional<Invite> invite = inviteRepositoryRepository.findByInviteCodeAndUsers_Id(event.getInvitedCode(), event.getUserId());
        invite.ifPresent(value -> deleteById(value.getId()));
    }

    public void deleteById(Long id) {
        inviteRepositoryRepository.deleteById(id);
    }
}
