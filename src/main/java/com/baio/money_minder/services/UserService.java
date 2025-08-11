package com.baio.money_minder.services;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.mappers.UserMapper;
import com.baio.money_minder.repositories.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public boolean validateCredentials(String email, String password) {
        var user = this.userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return false;
        }

        return Objects.equals(password, user.getPassword());
    }

    /**
     * @param email User email
     * @return Returns found UserDto or null otherwise
     */
    public UserDto findByEmail(@Email String email) {
        var user = this.userRepository.findByEmail(email).orElse(null);
        return user != null
                ? this.userMapper.toDto(user)
                : null;
    }

    public Iterable<UserDto> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.userMapper::toDto)
                .toList();
    }

    public UserDto createUser(RegisterUserRequest userRequest) {
        var newUser = this.userMapper.toEntity(userRequest);
        this.userRepository.save(newUser);

        return this.userMapper.toDto(newUser);
    }

    public UserDto updateUser(String email, UpdateUserRequest userRequest) {
        var user = this.userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return null;
        }

        this.userMapper.update(userRequest, user);
        this.userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto deleteUser(String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null) {
            return null;
        }

        userRepository.delete(user);
        return userMapper.toDto(user);
    }

    public UserDto changePassword(String email, ChangePasswordRequest request) {
        var user = this.userRepository.findByEmail(email).orElse(null);

        if(user == null || !request.getOldPassword().equals(user.getPassword())) {
            return null;
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return this.userMapper.toDto(user);
    }

    // GetAll

    // Get

    // Create

    // Update

    // Delete
}
