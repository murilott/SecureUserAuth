package com.example.secureuserauth.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.secureuserauth.entity.User;
import com.example.secureuserauth.enums.Role;
import com.example.secureuserauth.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (!repository.existsByName("admin")) {
            User user = User.newUser("admin",
                encoder.encode("admin"),
                "admin@email.com",
                List.of(Role.USER, Role.MODERATOR, Role.ADMIN));
                
            repository.save(user);

            log.info("Admin user created.");
        }
    }
}