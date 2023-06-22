package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.controller.dtos.requests.UsersRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.UsersResponseDTO;
import com.example.todofamilyapi.controller.mappers.UsersMapper;
import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.services.UserService;
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
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UsersMapper usersMapper;

    @PostMapping
    public UsersResponseDTO saveUser(@RequestBody UsersRequestDTO usersRequestDTO) {
        final Users entity = usersMapper.toEntity(usersRequestDTO);
        return usersMapper.fromEntity(userService.save(entity));
    }

    @GetMapping("{id}")
    public UsersResponseDTO findById(@PathVariable Long id) {
        return usersMapper.fromEntity(userService.findById(id));
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping
    public List<UsersResponseDTO> listAllUsers() {
        return userService.listAllUsers().stream().map(usersMapper::fromEntity).toList();
    }
}
