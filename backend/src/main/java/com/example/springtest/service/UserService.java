package com.example.springtest.service;

import com.example.springtest.exceptions.controller.NoSuchUserException;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public UserRole getUserRoleById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(NoSuchUserException::new).getRole();
    }
}

