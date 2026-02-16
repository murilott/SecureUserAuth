package com.example.secureuserauth.service.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.secureuserauth.exception.UserException;
import com.example.secureuserauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UserException {
        
        Long userId = Long.parseLong(username);

        return repository.findById(userId)
                .map(user -> (UserDetails) user)
                .orElseThrow(() -> new UserException("User not found"));
    }
}
