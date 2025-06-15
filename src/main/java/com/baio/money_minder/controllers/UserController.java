package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.UserDto;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getEmail(), user.getUsername()))
                .toList();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        var user = this.userRepository.findById(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = new UserDto(user.getEmail(), user.getUsername());
        return ResponseEntity.ok(userDto);
    }

//    @PostMapping("/users")
//    public User createUser() {
//
//    }
}
