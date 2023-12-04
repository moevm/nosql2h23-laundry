package com.example.springtest.service;

import com.example.springtest.model.Director;
import com.example.springtest.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director addDirector(Director director) {
        return directorRepository.addDirector(director.getFullName(), director.getPassword(), director.getEmail(), director.getCreationDate(), director.getEditDate(), director.getRole(), director.getPhone(), director.getSchedule());
    }


    public List<Director> getAllDirectors() {
        return (List<Director>) directorRepository.getAllDirectors();
    }

    @GetMapping("/getByLogin/{login}")
    public Director getDirectorByLogin(@PathVariable("login") String login) {
        return directorRepository.findByLogin(login).orElse(null);
    }

    @GetMapping("/getById/{id}")
    public Director getDirectorById(@PathVariable("id") Long id) {
        return directorRepository.getById(id).orElse(null);
    }
}

