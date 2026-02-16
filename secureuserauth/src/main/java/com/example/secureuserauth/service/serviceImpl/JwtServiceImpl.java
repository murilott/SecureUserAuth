package com.example.secureuserauth.service.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.example.secureuserauth.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET = 
        "super_secret_big_key_eeeeeeeeeeeeeee_256_secret";

    // private final long accessExpiration = 70000; // 70sec
    private final long accessExpiration = 600000; // 10min
    private final long refreshExpiration = 604800000; //7d

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    @Override
    public String generateAccessToken(Long id) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("type", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                // .claim("name", name)
                // .claim("email", email)
                // .claim("roles", roles.stream()
                //     .map(Enum::name)
                //     .toList())
                .signWith(key)       
                .compact();
    }

    @Override
    public String generateRefreshToken(Long id) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("type", "REFRESH")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +   refreshExpiration))
                // .claim("name", name)
                // .claim("email", email)
                // .claim("roles", roles.stream()
                //     .map(Enum::name)
                //     .toList())
                .signWith(key)       
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Claims getClaimsAllowExpired(String token) {
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    // @Override
    // public String extractName(String token) {
    //     return getClaims(token).get("name", String.class);
    // }

    // @Override
    // public String extractEmail(String token) {
    //     return getClaims(token).get("email", String.class);
    // }
    
    @Override
    public Long extractId(String token) {
        return Long.valueOf(getClaimsAllowExpired(token).getSubject());
    }

    // @Override
    // public List<String> extractRoles(String token) {
    //     Object roles = getClaims(token).get("roles");

    //     if (roles instanceof List<?> list) {
    //         return list.stream()
    //             .filter(String.class::isInstance)
    //             .map(String.class::cast)
    //             .toList();
    //     }

    //     return List.of();
    // }

    @Override
    public boolean isRefreshToken(String token) {
        return getClaimsAllowExpired(token).get("type", String.class).equals("REFRESH");
    }

    @Override
    public boolean isAccessToken(String token) {
        return getClaimsAllowExpired(token).get("type", String.class).equals("ACCESS");
    }


    @Override
    public boolean isTokenValid(String token, Long id) {
        return id.equals(extractId(token))
            && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
