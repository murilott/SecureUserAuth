package com.example.secureuserauth.dto.response;

import java.util.List;

import com.example.secureuserauth.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;
}
