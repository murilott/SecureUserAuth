package com.example.secureuserauth.service;

import java.util.List;

import com.example.secureuserauth.dto.request.RegisterUserRequestDto;
import com.example.secureuserauth.dto.response.UserResponseDto;

public interface UserService {
    public List<UserResponseDto> getAll();
    public UserResponseDto create(RegisterUserRequestDto userDto);
    public void delete(Long id);
}
