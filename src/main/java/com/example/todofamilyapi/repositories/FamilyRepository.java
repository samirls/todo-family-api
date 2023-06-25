package com.example.todofamilyapi.repositories;

import com.example.todofamilyapi.entities.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    List<Family> findAllByUsers_Email(String email);

}
