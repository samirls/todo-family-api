package com.example.todofamilyapi.services;

import com.example.todofamilyapi.entities.Todo;
import com.example.todofamilyapi.exceptions.TodoNotFoundException;
import com.example.todofamilyapi.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo save(Todo todo, Principal principal) {
        todo.setOwner(principal.getName());
        return todoRepository.save(todo);
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("TODO not found"));
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> listAllTodo() {
        return todoRepository.findAll();
    }

    public void changeStatus(Long id, Boolean status) {
        Todo todo = findById(id);
        todo.setConcluded(status);
        todoRepository.save(todo);
    }
}
