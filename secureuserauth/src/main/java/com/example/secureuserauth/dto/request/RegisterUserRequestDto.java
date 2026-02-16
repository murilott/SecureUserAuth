package com.example.secureuserauth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterUserRequestDto {
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Email must not be blank")
    @Email
    private String email;
}
