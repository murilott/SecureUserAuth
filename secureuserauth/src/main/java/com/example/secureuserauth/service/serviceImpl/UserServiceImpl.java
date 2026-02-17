package com.example.secureuserauth.service.serviceImpl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.secureuserauth.dto.request.RegisterUserRequestDto;
import com.example.secureuserauth.dto.response.PostResponseDto;
import com.example.secureuserauth.dto.response.UserResponseDto;
import com.example.secureuserauth.entity.Post;
import com.example.secureuserauth.entity.User;
import com.example.secureuserauth.enums.Role;
import com.example.secureuserauth.exception.UserException;
import com.example.secureuserauth.mapper.PostMapper;
import com.example.secureuserauth.mapper.UserMapper;
import com.example.secureuserauth.repository.UserRepository;
import com.example.secureuserauth.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PostMapper postMapper;

    private final PasswordEncoder encoder;

    @Override
    public List<UserResponseDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public UserResponseDto create(RegisterUserRequestDto userDto) {
        if (repository.existsByEmail(userDto.getEmail().trim().toLowerCase())) {
            throw new UserException("User already exists with email " + userDto.getEmail().trim().toLowerCase());
        }

        User user = User.newUser(userDto.getName(),
                encoder.encode(userDto.getPassword()),
                userDto.getEmail().trim().toLowerCase(),
                List.of(Role.USER));
        User savedUser = repository.save(user);

        log.info("User created successfully: {}", mapper.toDto(savedUser).toString());

        return mapper.toDto(savedUser);
    }

    @Override
    public List<PostResponseDto> getUserPosts(Long userId) {
        User user = repository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Post> posts = user.getPosts();

        return postMapper.toDtoList(posts);
    }


    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }
}
