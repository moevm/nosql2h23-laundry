package com.example.springtest.controller;

import com.example.springtest.dto.clientService.NewClientData;
import com.example.springtest.dto.login.LoginResponse;
import com.example.springtest.dto.signUp.SignUpRequest;
import com.example.springtest.dto.signUp.SignUpResponse;
import com.example.springtest.exceptions.controller.NoSuchUserException;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.Client;
import com.example.springtest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/api/sign_up")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {

        Optional<Client> client = clientService.getClientByLogin(request.getLogin());

        if (client.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Client newClient = clientService.addClient(
                NewClientData.builder()
                        .login(request.getLogin())
                        .password(request.getPassword())
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .build()
        );

        return SignUpResponse.builder()
                .id(newClient.getId())
                .build();
    }

    @GetMapping("/api/clients/getAll")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}
