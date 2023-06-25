package com.example.todofamilyapi.repositories;

import com.example.todofamilyapi.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepositoryRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUsers_Email(String email);

    Optional<Invite> findByInviteCodeAndUsers_Id(String inviteCode, Long id);

}
