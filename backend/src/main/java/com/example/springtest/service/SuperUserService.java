package com.example.springtest.service;

import com.example.springtest.model.SuperUser;
import com.example.springtest.repository.SuperUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class SuperUserService {

    private final SuperUserRepository superUserRepository;

    @Autowired
    public SuperUserService(SuperUserRepository superUserRepository) {
        this.superUserRepository = superUserRepository;
    }

    public SuperUser addSuperUser(SuperUser superUser) {
        return superUserRepository.addSuperUser(superUser.getFullName(), superUser.getPassword(), superUser.getEmail(), superUser.getCreationDate(), superUser.getEditDate(), superUser.getRole(), superUser.getPhone(), superUser.getSchedule());
    }


    public List<SuperUser> getAllSuperUsers() {
        return (List<SuperUser>) superUserRepository.getAllSuperUsers();
    }

    @GetMapping("/getByLogin/{login}")
    public SuperUser getSuperUserByLogin(@PathVariable("login") String login) {
        return superUserRepository.findByLogin(login).orElse(null);
    }

    @GetMapping("/getById/{id}")
    public SuperUser getSuperUserById(@PathVariable("id") Long id) {
        return superUserRepository.getById(id).orElse(null);
    }
}

