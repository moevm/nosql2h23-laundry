package com.example.springtest.controller;

import com.example.springtest.model.User;
import com.example.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getByLogin/{login}")
    public User getUserByLogin(@PathVariable("login") String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}
