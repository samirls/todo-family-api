package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public Users findById(Long id) {
        return usersRepository.findById(id).get();
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public List<Users> listAllUsers() {
        return usersRepository.findAll();
    }
}
