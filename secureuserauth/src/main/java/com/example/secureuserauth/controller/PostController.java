package com.example.secureuserauth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secureuserauth.dto.request.CreatePostRequestDto;
import com.example.secureuserauth.dto.response.PostResponseDto;
import com.example.secureuserauth.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService service;
    
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping()
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostRequestDto postDto) {
        PostResponseDto post = service.create(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
