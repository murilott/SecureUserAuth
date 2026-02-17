package com.example.secureuserauth.service;

import java.util.List;

import com.example.secureuserauth.dto.request.CreatePostRequestDto;
import com.example.secureuserauth.dto.response.PostResponseDto;

public interface PostService {
    public List<PostResponseDto> getAll();
    public PostResponseDto create(CreatePostRequestDto postDto);
    public boolean delete(Long postId, Long userId);
}
