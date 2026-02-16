package com.example.secureuserauth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDto {
    @NotBlank(message = "Email must not be blank")
    @Email
    private String email;
    @NotBlank(message = "Password must not be blank")
    private String password;
}
