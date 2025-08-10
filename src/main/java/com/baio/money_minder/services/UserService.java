package com.baio.money_minder.services;

import com.baio.money_minder.dtos.LoginRequest;
import com.baio.money_minder.dtos.UserDto;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.mappers.UserMapper;
import com.baio.money_minder.repositories.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

        this.userRepository.save(new User("andres435b@gmail.com", "BAIO", "Cocona"));
        this.userRepository.save(new User("marco@gmail.com", "Maga", "rana"));
        this.userRepository.save(new User("emanuel@gmail.com", "Belcas", "Belcoso"));
        this.userRepository.save(new User("john@gmail.com", "JohnDoe", "123"));
    }

    public UserDto findByEmail(@Email String email) {
        var user = this.userRepository.findByEmail(request.getEmail()).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        // We use the .equals method instead of a more straightforward != comparison because the .equals method
        // has null validation
        if(!user.getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean validateCredentials(LoginRequest request) {

    }

    // GetAll

    // Get

    // Create

    // Update

    // Delete
}
