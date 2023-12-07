package com.example.springtest.service;

import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.client.GetAllRequest;
import com.example.springtest.dto.client.GetTotalClientsCountRequest;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.ClientRepository;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    @Transactional
    public List<Client> getAllClients(GetAllRequest request) {
        return clientRepository.getAllClients(request.getName(), request.getEmail(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
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
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public Client createClient(CreateClientRequest request) {

        Optional<User> userOptional = userRepository.findByLogin(request.getLogin());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        ZonedDateTime creationDateTime = ZonedDateTime.now();

        return clientRepository.addClient(UUID.randomUUID(), UserRole.CLIENT, request.getLogin(), request.getPassword(), request.getName(), request.getEmail(), creationDateTime, creationDateTime);

    }

    @Transactional
    public long getTotalCount(GetTotalClientsCountRequest request) {
        int count = clientRepository.getTotalCount(request.getName(), request.getEmail());

        return (int) (Math.ceil(count / (double) request.getElementsOnPage()));
    }

    @Transactional
    public void deleteClients(List<UUID> idList) {
        clientRepository.deleteClients(idList);
    }


}

