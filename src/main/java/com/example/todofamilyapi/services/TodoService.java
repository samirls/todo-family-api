package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Todo;
import com.example.todofamilyapi.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id).get();
    }

    public void deleteUserById(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> listAllTodo() {
        return todoRepository.findAll();
    }
}
