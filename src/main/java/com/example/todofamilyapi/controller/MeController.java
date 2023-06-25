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

    /**
     * Retorna o usuário logado. Esse método é usado várias vezes no frontend para verificação se o token está válido,
     * caso ele nao consiga acessar esse endpoint, ele redireciona para o login. (essa regra está no front)
     * @param principal
     * @return
     */
    @GetMapping
    public UsersResponseDTO findById(Principal principal) {
        final var user = userService.findByEmail(principal.getName());

        // Se o usuário não existir, retorna null, mas isso nunca vai acontecer porque se o cara já está logado ele existe.
        // Esse retorno também vem a lista de todos os convites do usuário, entao ele é usado para atualizar a lista de convites do usuário também.
        return user.map(userMapper::fromEntity).orElse(null);
    }
}
