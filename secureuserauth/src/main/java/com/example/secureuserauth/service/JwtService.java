package com.example.secureuserauth.service;

public interface JwtService {

    public String generateAccessToken(Long id);
    public String generateRefreshToken(Long id);
    // public String extractName(String token);
    // public String extractEmail(String token);
    public Long extractId(String token);
    // public List<String> extractRoles(String token);
    public boolean isRefreshToken(String token);
    public boolean isAccessToken(String token);
    public boolean isTokenValid(String token, Long id);
    public boolean isTokenExpired(String token);
}
