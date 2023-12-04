package com.example.springtest.service;

import com.example.springtest.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.springtest.model.Client;
import com.example.springtest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client addClient(Client client) {
        return clientRepository.addClient(client.getFullName(), client.getPassword(), client.getEmail(), client.getCreationDate(), client.getEditDate());
    }


    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.getAllClients();
    }

    @GetMapping("/getByLogin/{login}")
    public Client getClientByLogin(@PathVariable("login") String login) {
        return clientRepository.findByLogin(login).orElse(null);
    }

    @GetMapping("/getById/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        return clientRepository.getById(id).orElse(null);
    }
}

