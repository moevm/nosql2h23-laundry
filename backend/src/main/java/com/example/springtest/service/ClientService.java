package com.example.springtest.service;

import com.example.springtest.dto.clientService.NewClientData;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.User;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.springtest.model.Client;
import com.example.springtest.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    @Transactional
    public Client addClient(NewClientData data) {

        LocalDateTime localDateTime = LocalDateTime.now();

        Optional<User> userOptional = userRepository.findByLogin(data.getLogin());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Client client = Client.clientBuilder()
                .id(UUID.randomUUID())
                .login(data.getLogin())
                .password(data.getPassword())
                .email(data.getEmail())
                .fullName(data.getFullName())
                .creationDate(localDateTime)
                .editDate(localDateTime)
                .build();

        return clientRepository.addClient(client.getId(), client.getRole(), client.getLogin(), client.getPassword(), client.getFullName(), client.getEmail(), client.getCreationDate(), client.getEditDate());
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
}

