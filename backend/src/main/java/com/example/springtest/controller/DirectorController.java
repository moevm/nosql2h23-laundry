package com.example.springtest.controller;

import com.example.springtest.model.Admin;
import com.example.springtest.model.Client;
import com.example.springtest.model.Director;
import com.example.springtest.service.AdminService;
import com.example.springtest.service.ClientService;
import com.example.springtest.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping("/add")
    public @ResponseBody Director addDirector(@RequestBody Director director) {
        return directorService.addDirector(director);
    }

    @GetMapping("/getAll")
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/findByLogin/{login}")
    public Director getDirectorByLogin(@PathVariable("login") String login) {
        return directorService.getDirectorByLogin(login);
    }

    @GetMapping("/findById/{id}")
    public Director getDirectorById(@PathVariable("id") Long id) {
        return directorService.getDirectorById(id);
    }
}
