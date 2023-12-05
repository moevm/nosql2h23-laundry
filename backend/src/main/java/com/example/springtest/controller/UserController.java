package com.example.springtest.controller;

import com.example.springtest.model.User;
import com.example.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(UUID.fromString(id));
    }
}
