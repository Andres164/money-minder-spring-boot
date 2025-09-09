package com.baio.money_minder.users;

import com.baio.money_minder.notifications.Notification;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Long id;

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
    @JsonManagedReference
    private List<Notification> notifications;
}
