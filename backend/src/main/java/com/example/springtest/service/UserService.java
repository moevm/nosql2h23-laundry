package com.example.springtest.service;

import com.example.springtest.model.User;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}

