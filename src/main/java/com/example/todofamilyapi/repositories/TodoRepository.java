package com.example.todofamilyapi.repositories;

import com.example.todofamilyapi.entities.Family;
import com.example.todofamilyapi.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
