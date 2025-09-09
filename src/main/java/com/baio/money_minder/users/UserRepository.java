package com.baio.money_minder.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
