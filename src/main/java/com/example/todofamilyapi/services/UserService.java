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

    public Users save(Users users){
        Users save = usersRepository.save(users);
        return save;
    }

    public Users findById(Long id){
        Users findUser = usersRepository.findById(id).get();
        return findUser;
    }

    public void deleteUserById(Long id){
        usersRepository.deleteById(id);
    }

    public List<Users> listAllUsers(){
        List<Users> all = usersRepository.findAll();
        return all;
    }

}
