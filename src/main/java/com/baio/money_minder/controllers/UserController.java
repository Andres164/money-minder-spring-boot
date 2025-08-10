package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {

        if(!this.userService.validateCredentials(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(this.userService.findByEmail(request.getEmail()));
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers() {
        return userService.findAll()
                .stream()
                .map(this.userMapper::toDto)
                .toList();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        var user = this.userService.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
        @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder) {
        if(this.userService.findByEmail(request.getEmail()).orElse(null) != null) {
            return ResponseEntity.badRequest().build();
        }

        var newUser = this.userMapper.toEntity(request);
        this.userService.save(newUser);

        var userDto = this.userMapper.toDto(newUser);
        var userUri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(userUri).body(userDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable(name = "email") String email,
        @RequestBody UpdateUserRequest request) {
        var user = userService.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userService.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") String email) {
        var user = userService.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{email}/change-password")
    public ResponseEntity<Void> changePassword(
        @PathVariable(name = "email") String email,
        @RequestBody ChangePasswordRequest request) {
        var user = userService.findByEmail(email).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        // We use the .equals method instead of a more straightforward != comparison because the .equals method
        // has null validation
        if(!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userService.save(user);

        return ResponseEntity.noContent().build();
    }
}