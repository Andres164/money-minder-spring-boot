package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Tag(name = "Users", description = "Access users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            description = "Endpoint for verifying logging-in credentials for a user",
            responses = {
                    @ApiResponse( responseCode = "200"),
                    @ApiResponse( responseCode = "401", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {

        if(!this.userService.validateCredentials(request.getEmail(), request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(this.userService.findByEmail(request.getEmail()));
    }

    @Operation( description = "Endpoint for getting all users")
    @GetMapping
    public Iterable<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            description = "Endpoint for fetching a user by its email",
            responses = {
                    @ApiResponse( responseCode = "200"),
                    @ApiResponse( responseCode = "204", content = @Content)
            }
    )
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        var user = this.userService.findByEmail(email);

        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @Operation(
            description = "Endpoint for creating a new user",
            responses = {
                    @ApiResponse( responseCode = "201"),
                    @ApiResponse( responseCode = "400", content = @Content)
            }
    )
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

    @Operation(
            description = "Endpoint for updating all fields of the user with the given email",
            responses = {
                    @ApiResponse( responseCode = "200"),
                    @ApiResponse( responseCode = "404", content = @Content)
            }
    )
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

    @Operation(
            description = "Endpoint for deleting the user with the given email",
            responses = {
                    @ApiResponse( responseCode = "204"),
                    @ApiResponse( responseCode = "404", content = @Content)
            }
    )
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") String email) {
        var user = userService.deleteUser(email);
        return user != null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
            description = "Endpoint for changing the user password, the provided old password must match the current user's password ",
            responses = {
                    @ApiResponse( responseCode = "204"),
                    @ApiResponse( responseCode = "401", content = @Content)
            }
    )
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