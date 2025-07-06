package com.baio.money_minder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    public User(String email, String username, String password) {
        this.email  = email;
        this.username  = username;
        this.password  = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(
        name = "email",
        length = 150,
        unique = true
    )
    private String email;

    @Column(
        name = "username",
        length = 50
    )
    private String username;

    @Column(
        name = "password",
        length = 50
    )
    private String password;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Notification> notifications;
}
