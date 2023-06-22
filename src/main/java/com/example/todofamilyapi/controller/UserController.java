package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public Users saveUser(@RequestBody Users users) {
        return userService.save(users);
    }

    @GetMapping("/find/{id}")
    public Users findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/find-all-users")
    public List<Users> listAllUsers() {
        return userService.listAllUsers();
    }
}
