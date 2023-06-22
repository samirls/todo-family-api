package com.example.todofamilyapi;

import com.example.todofamilyapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoFamilyApiApplication {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TodoFamilyApiApplication.class, args);
    }

    @Bean
    protected InitializingBean sendDataBase() {
        return userService::insertAdminUser;
    }


}
