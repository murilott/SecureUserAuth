package com.example.secureuserauth.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.secureuserauth.dto.request.CreatePostRequestDto;
import com.example.secureuserauth.dto.response.PostResponseDto;
import com.example.secureuserauth.entity.Post;
import com.example.secureuserauth.entity.User;
import com.example.secureuserauth.mapper.PostMapper;
import com.example.secureuserauth.repository.PostRepository;
import com.example.secureuserauth.repository.UserRepository;
import com.example.secureuserauth.service.PostService;
import com.example.secureuserauth.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    @Override
    public List<PostResponseDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public PostResponseDto create(CreatePostRequestDto postDto) {
        User author = userRepository
            .findById(postDto.getAuthorId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = Post.newPost(author,
                postDto.getTitle(),
                postDto.getContent());
        Post savedPost = repository.save(post);

        log.info("Post created successfully: {}", mapper.toDto(savedPost).toString());

        return mapper.toDto(savedPost);
    }

    @Override
    public boolean delete(Long postId, Long userId) {
        User user = userRepository
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        Post post = repository
            .findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        
        boolean isAuthorized = false;

        if (user.isAdmin() || post.getAuthor().getId().equals(user.getId())) {
            isAuthorized = true;
        }

        if (isAuthorized) {
            user.getPosts().remove(post);
            repository.delete(post);

            userRepository.save(user);

            return true;
        } else {
            throw new RuntimeException("User unauthorized, cannot delete post. Roles=" + user.getRoles().toString());
        }
    }
}
