package com.example.secureuserauth.service.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.secureuserauth.dto.response.TokenPairResponse;
import com.example.secureuserauth.dto.response.TokenResponseDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.User;
import com.example.secureuserauth.exception.UserException;
import com.example.secureuserauth.mapper.UserMapper;
import com.example.secureuserauth.repository.UserRepository;
import com.example.secureuserauth.service.AuthService;
import com.example.secureuserauth.service.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final UserMapper mapper;

    @Override
    public TokenPairResponse login(String email, String password) {
        User user = repository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UserException("User not found or invalid email."));

        if (!encoder.matches(password, user.getPassword())) {
            throw new UserException("Invalid email or password.");
        }

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        log.info("Access and refresh token issued to userId {} at {}",
            user.getId(), 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );

        return new TokenPairResponse(refreshToken, accessToken);
    }

    @Override
    public TokenResponseDto refresh(String refreshToken) {
        Long userId = jwtService.extractId(refreshToken);
        // User user = repository.findById(userId);

        String newAccessToken = jwtService.generateAccessToken(userId);

        log.info("Access token issued to userId {} at {}",
            userId, 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );
        
        return new TokenResponseDto(newAccessToken);
    }
    
    @Override
    public UserResponseDto me(User user) {
        log.info("Obtaining /me user: {}",
            user.getName() 
        );

        return mapper.toDto(user);
    }
}
