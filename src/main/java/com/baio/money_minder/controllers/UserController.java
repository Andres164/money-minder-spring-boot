package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.User;
import com.baio.money_minder.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.userRepository.save(new User("andres435b@gmail.com", "BAIO", "Cocona"));
        this.userRepository.save(new User("marco@gmail.com", "Maga", "putos"));
        this.userRepository.save(new User("emanuel@gmail.com", "Belcas", "Belcoso"));
        this.userRepository.save(new User("john@gmail.com", "JohnDoe", "123"));
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return this.userRepository.findById(email).orElse(null);
    }

//    @PostMapping("/users")
//    public User createUser() {
//
//    }
}
