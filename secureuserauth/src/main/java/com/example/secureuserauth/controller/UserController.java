package com.example.secureuserauth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secureuserauth.dto.request.RegisterUserRequestDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService service;
    
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody RegisterUserRequestDto userDto) {
        UserResponseDto user = service.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
