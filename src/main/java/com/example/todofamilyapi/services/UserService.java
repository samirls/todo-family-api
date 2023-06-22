package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Users;
import com.example.todofamilyapi.exceptions.UserNotFoundException;
import com.example.todofamilyapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ADMIN = "admin@admin.com";

    private final UsersRepository usersRepository;

    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public Users findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public List<Users> listAllUsers() {
        return usersRepository.findAll();
    }

    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found!"));
    }

    public Optional<Users> findByEmailOptional(String email) {
        return usersRepository.findByEmail(email);
    }
    public Boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public void insertAdminUser() {
        if (findByEmailOptional(ADMIN).isEmpty()) {
            log.debug("Administrator user not found, creating...");
            final var user = new Users();
            user.setName("Administrator");
            user.setAdmin(true);
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            user.setEmail("admin@admin.com");
            save(user);
        } else {
            log.info("insertAdminUser: admin user already exists");
        }
    }
}
