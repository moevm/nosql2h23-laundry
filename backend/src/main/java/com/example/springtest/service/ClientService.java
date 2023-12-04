package com.example.springtest.service;

import com.example.springtest.dto.clientService.NewClientData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.springtest.model.Client;
import com.example.springtest.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client addClient(NewClientData data) {
        // TODO: use data!
//        return clientRepository.addClient(client.getFullName(), client.getPassword(), client.getEmail(), client.getCreationDate(), client.getEditDate());

        return Client.clientBuilder().id("SFGdfgfg").build();
    }

    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Transactional
    public Optional<Client> getClientByLogin(String login) {
        return clientRepository.findByLogin(login);
    }

    @Transactional
    public Optional<Client> getClientById(Long id) {
        return clientRepository.getById(id);
    }
}

