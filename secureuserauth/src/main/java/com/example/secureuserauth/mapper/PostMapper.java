package com.example.secureuserauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.secureuserauth.dto.response.PostResponseDto;
import com.example.secureuserauth.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.name", target = "authorName")
    PostResponseDto toDto(Post post);

    List<PostResponseDto> toDtoList(List<Post> post);
}
