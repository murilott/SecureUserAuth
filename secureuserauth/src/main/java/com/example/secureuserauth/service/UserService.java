package com.example.secureuserauth.service;

import java.util.List;

import com.example.secureuserauth.dto.request.RegisterUserRequestDto;
import com.example.secureuserauth.dto.response.PostResponseDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.User;

public interface UserService {
    public List<UserResponseDto> getAll();
    public UserResponseDto create(RegisterUserRequestDto userDto);
    public void delete(Long id);
    public List<PostResponseDto> getUserPosts(Long userId);
}
