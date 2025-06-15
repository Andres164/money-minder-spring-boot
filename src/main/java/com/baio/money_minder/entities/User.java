package com.baio.money_minder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @Column(
        name = "email",
        length = 150
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
}
