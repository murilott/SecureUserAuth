package com.example.secureuserauth.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponseDto {
    private Long id;

    private String authorName;
    private String authorId;

    private String title;

    private String content;
    
    private LocalDateTime createdAt;
}