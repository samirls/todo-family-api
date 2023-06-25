package com.example.todofamilyapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDate.now;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Size(max = 50)
    @NotBlank
    @Column(unique = true)
    private String email;

    @Size(max = 100)
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean admin = false;

    @Column(nullable = false)
    private boolean active = true;

    private LocalDateTime lastAccess;

    @Column(updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "users")
    private List<Invite> invites;

    @ManyToMany
    @JoinTable(
            name = "users_family",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id")
    )
    private List<Family> family;

    @PrePersist
    public void prePersist() {
        this.createdAt = now();
    }

    public void lastAccess() {
        this.lastAccess = LocalDateTime.now();
    }

    public Users(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
