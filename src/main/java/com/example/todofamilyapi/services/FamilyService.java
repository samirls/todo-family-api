package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.entities.Todo;
import com.example.todofamilyapi.events.InviteDeletedEvent;
import com.example.todofamilyapi.exceptions.FamilyNotFoundException;
import com.example.todofamilyapi.exceptions.PermissionDeniedException;
import com.example.todofamilyapi.repositories.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    /**
     * Para criar uma familia é necessario informar o nome, mas ela estava sendo criada sem vincular um usuário.
     * Mas agora ela é criada vinculando o usuário que está logado no sistema (ou que fez a solicitação para criar a familia)
     * @param family objeto family que está sendo criado.
     * @param principal principal é o usuário que está logado no sistema.
     * @return
     */
    public Family save(Family family, Principal principal) {
        userService.findByEmail(principal.getName()).ifPresent(user -> family.setUsers(List.of(user)));

        // ao criar uma familia o usuário que está logado no sistema é vinculado como dono da familia.
        family.setFamilyOwner(principal.getName());
        return familyRepository.save(family);
    }

    public Family findById(Long id) {
        return familyRepository.findById(id).orElseThrow(() -> new FamilyNotFoundException("family not found!"));
    }

    /**
     * Para deletar uma familia é necessário informar o id da familia e o usuário que está logado no sistema.
     * Não será possivel deletar uma família caso o usuário que está logado no sistema não seja o dono da família.
     * @param id id é o id da familia que está sendo deletada.
     * @param pricipal pricipal é o usuário que está logado no sistema.
     */
    public void deleteById(Long id, Principal pricipal) {
        if (!isOwner(id, pricipal)) {
            throw new PermissionDeniedException("you are not the owner of this family!");
        }
        familyRepository.deleteById(id);
    }

    public List<Family> listAllFamily(Principal principal) {
        final List<Family> families = familyRepository.findAllByUsers_Email(principal.getName());

        /* ordenando a lista de "todos" sempre por data de criação */
        for (Family family : families) {
            family.getTodos().sort(Comparator.comparing(Todo::getCreatedAt));
        }

        return families;
    }

    /**
     * Para vincular uma família é necessário informar o id do usuário, o id da família e o código da família.
     * @param userId userId é o id do usuário que está sendo vinculado a família.
     * @param familyId familyId é o id da família que está sendo vinculada ao usuário.
     * @param familyCode familyCode é o código da família que está sendo vinculada ao usuário. Esse código é gerado automaticamente quando a família é criada.
     */
    public void vinculateFamily(Long userId, Long familyId, String familyCode) {
        Family family = findById(familyId);
        family.getUsers().add(userService.findById(userId));

        if (family.getFamilyCode().equals(familyCode)) {
            familyRepository.save(family);

           /* ao executar o método vinculateFamily e caso de sucesso o evento InviteDeletedEvent é publicado para informar que o convite foi aceito.
                Entao ele envia o id e codigo da familia para deletar na classe InviteService */
            eventPublisher.publishEvent(new InviteDeletedEvent(this, userId, familyCode));
        } else {
            throw new FamilyNotFoundException("invalid family code!");
        }
    }

    /**
     * Essa é a verificação para saber se o usuário que está logado no sistema é o dono da família.
     * @param familyId familyId é o id da família que está sendo verificada.
     * @param principal principal é o usuário que está logado no sistema.
     * @return retorna true caso o usuário que está logado no sistema seja o dono da família.
     */
    boolean isOwner(Long familyId, Principal principal) {
        return findById(familyId).getFamilyOwner().equals(principal.getName());
    }
}
