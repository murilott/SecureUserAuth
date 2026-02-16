package com.example.secureuserauth.service;

import com.example.secureuserauth.dto.response.TokenPairResponse;
import com.example.secureuserauth.dto.response.TokenResponseDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.User;

public interface AuthService {
    public TokenPairResponse login(String email, String password);
    public TokenResponseDto refresh(String refreshToken);
    public UserResponseDto me(User user);
}
