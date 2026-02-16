package com.example.secureuserauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.secureuserauth.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
}
