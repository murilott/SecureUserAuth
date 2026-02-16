package com.example.secureuserauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.secureuserauth.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    // Optional<UserDetails> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
}
