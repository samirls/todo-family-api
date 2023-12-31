package com.example.todofamilyapi.controller;


import com.example.todofamilyapi.controller.dtos.requests.LoginRequestDTO;
import com.example.todofamilyapi.controller.dtos.requests.SignupUserRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.MessageResponse;
import com.example.todofamilyapi.controller.mappers.UsersMapper;
import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.security.jwt.JWTUtils;
import com.example.todofamilyapi.security.services.UserDetailsImpl;
import com.example.todofamilyapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final UsersMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<Void> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        lastAccess(authentication);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(null);
    }

    /**
     * Neste método é adicionado a data da ultima autenticação do usuário no banco. Toda vez que ele se loga (no método "authenticateUser" acima) é atualizado.
     * @param authentication - Objeto de autenticação do Spring Security
     */
    private void lastAccess(Authentication authentication) {
        final Optional<Users> user = userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).username());
        if (user.isPresent()) {
            user.get().lastAccess();
            userService.save(user.get());
        }
    }


    /**
     * Método para registrar um novo usuário (ele não tem segurança ativa) e retorna o usuário criado.
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupUserRequestDTO signUpRequestDTO) {
        if (Boolean.TRUE.equals(userService.existsByEmail(signUpRequestDTO.email()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        final Users user = userMapper.toEntity(signUpRequestDTO);

        //fixme aqui tem um bug neste return, caso o usuário seja criado ele retorna o JSON com o campo password.
        // Ele deve ser removido pois o usuário nao deve ter acesso a ele mesmo ele mesmo tendo criado.
        // O ideal é que retorne um DTO com os dados do usuário sem o password.
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("You've been signed out!"));
    }
}
