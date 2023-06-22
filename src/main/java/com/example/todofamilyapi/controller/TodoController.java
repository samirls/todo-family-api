package com.example.todofamilyapi.controller;

import com.example.todofamilyapi.controller.dtos.requests.TodoRequestDTO;
import com.example.todofamilyapi.controller.dtos.responses.TodoResponseDTO;
import com.example.todofamilyapi.controller.mappers.TodoMapper;
import com.example.todofamilyapi.entities.Todo;
import com.example.todofamilyapi.services.TodoService;
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
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper todosMapper;

    @PostMapping
    public TodoResponseDTO saveUser(@RequestBody TodoRequestDTO todoRequestDTO) {
        final Todo entity = todosMapper.toEntity(todoRequestDTO);
        return todosMapper.fromEntity(todoService.save(entity));
    }

    @GetMapping("/find/{id}")
    public TodoResponseDTO findById(@PathVariable Long id) {
        return todosMapper.fromEntity(todoService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        todoService.deleteUserById(id);
    }

    @GetMapping("/find-all-todos")
    public List<TodoResponseDTO> listAllTodo() {
        return todoService.listAllTodo().stream().map(todosMapper::fromEntity).toList();
    }
}
