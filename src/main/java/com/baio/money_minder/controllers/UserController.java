package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.repositories.UserRepository;
import com.baio.money_minder.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

        this.userRepository.save(new User("andres435b@gmail.com", "BAIO", "Cocona"));
        this.userRepository.save(new User("marco@gmail.com", "Maga", "rana"));
        this.userRepository.save(new User("emanuel@gmail.com", "Belcas", "Belcoso"));
        this.userRepository.save(new User("john@gmail.com", "JohnDoe", "123"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        var user = this.userRepository.findByEmail(request.getEmail()).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        // We use the .equals method instead of a more straightforward != comparison because the .equals method
        // has null validation
        if(!user.getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this.userMapper::toDto)
                .toList();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        var user = this.userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
        @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder) {
        if(this.userRepository.findByEmail(request.getEmail()).orElse(null) != null) {
            return ResponseEntity.badRequest().build();
        }

        var newUser = this.userMapper.toEntity(request);
        this.userRepository.save(newUser);

        var userDto = this.userMapper.toDto(newUser);
        var userUri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(userUri).body(userDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable(name = "email") String email,
        @RequestBody UpdateUserRequest request) {
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{email}/change-password")
    public ResponseEntity<Void> changePassword(
        @PathVariable(name = "email") String email,
        @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        // We use the .equals method instead of a more straightforward != comparison because the .equals method
        // has null validation
        if(!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}

/**
 * TODO (User) Update  y Delete
 */