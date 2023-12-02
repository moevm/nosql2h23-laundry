package com.example.springtest.controller;

import com.example.springtest.model.Admin;
import com.example.springtest.model.SuperUser;
import com.example.springtest.service.AdminService;
import com.example.springtest.service.SuperUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superUsers")
public class SuperUserController {

    private final SuperUserService superUserService;

    @Autowired
    public SuperUserController(SuperUserService superUserService) {
        this.superUserService = superUserService;
    }

    @PostMapping("/add")
    public @ResponseBody SuperUser addSuperUser(@RequestBody SuperUser superUser) {
        return superUserService.addSuperUser(superUser);
    }

    @GetMapping("/getAll")
    public List<SuperUser> getAllSuperUsers() {
        return superUserService.getAllSuperUsers();
    }

    @GetMapping("/findByLogin/{login}")
    public SuperUser getSuperUserByLogin(@PathVariable("login") String login) {
        return superUserService.getSuperUserByLogin(login);
    }

    @GetMapping("/findById/{id}")
    public SuperUser getSuperUserById(@PathVariable("id") Long id) {
        return superUserService.getSuperUserById(id);
    }
}
