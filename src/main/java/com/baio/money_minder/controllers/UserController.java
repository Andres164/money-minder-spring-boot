package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.services.UserService;
import lombok.AllArgsConstructor;
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

        if(!this.userService.validateCredentials(request.getEmail(), request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(this.userService.findByEmail(request.getEmail()));
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        var user = this.userService.findByEmail(email);

        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
        @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        if(this.userService.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var userDto = this.userService.createUser(request);
        var userUri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(userUri).body(userDto);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable(name = "email") String email,
        @RequestBody UpdateUserRequest request
    ) {
        var user = userService.updateUser(email, request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") String email) {
        var user = userService.deleteUser(email);
        return user != null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{email}/change-password")
    public ResponseEntity<Void> changePassword(
        @PathVariable(name = "email") String email,
        @RequestBody ChangePasswordRequest request
    ) {
        boolean changedPassword = this.userService.changePassword(email, request) != null;

        return changedPassword
                ? ResponseEntity.noContent().build()
                : new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    }
}