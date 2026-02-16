package com.example.secureuserauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenPairResponse {
    private String refreshToken;
    private String accessToken;
}
