package com.example.springtest.service;

import org.springframework.stereotype.Service;

import com.example.springtest.model.Client;
import com.example.springtest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Метод для добавления пользователя
    public Client addClient(Client client) {
        return clientRepository.addClientCustomQuery(client.getFullName(), client.getPassword(), client.getEmail(), client.getCreationDate(), client.getEditDate());
    }

    // Метод для получения всех пользователей
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.getAllClientsCustomQuery();
    }
}

