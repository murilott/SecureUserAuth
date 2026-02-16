package com.example.secureuserauth.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.secureuserauth.dto.request.LoginRequestDto;
import com.example.secureuserauth.dto.response.TokenPairResponse;
import com.example.secureuserauth.dto.response.TokenResponseDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.User;
import com.example.secureuserauth.service.AuthService;
import com.example.secureuserauth.service.JwtService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    private final JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginDto,
        HttpServletResponse response
    ) {
        // return ResponseEntity.status(HttpStatus.CREATED).body(
        //     service.login(loginDto.getEmail(), loginDto.getPassword())
        // );
        TokenPairResponse tokens = service.login(loginDto.getEmail().trim().toLowerCase(), loginDto.getPassword());

        // Criar cookie aqui no controller
        ResponseCookie cookie = ResponseCookie.from("refresh_token", tokens.getRefreshToken())
            .httpOnly(true)
            .secure(false)
            .path("/auth/refresh")
            .sameSite("Lax")
            .maxAge(Duration.ofDays(7))
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        log.info("User {} successfully logged on at {}",
            loginDto.getEmail(), 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );

        return ResponseEntity.ok(new TokenResponseDto(tokens.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
        @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {
        log.info("Refresh request for token {}", refreshToken);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!jwtService.isTokenValid(refreshToken, jwtService.extractId(refreshToken)) ||
            !jwtService.isRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(service.refresh(refreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.me(user));
    }
}
