package com.example.secureuserauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // @Mapping(target = "roles", source = "roles")
    // @Mapping(target = "authorities", ignore = true)
    // User toEntity(UserResponseDto user);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toDtoList(List<User> user);
}
