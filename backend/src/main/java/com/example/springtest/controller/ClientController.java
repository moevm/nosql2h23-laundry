package com.example.springtest.controller;

import com.example.springtest.dto.client.*;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import com.example.springtest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/api/client/create")
    public void createClient(@RequestBody CreateClientRequest request) {
        clientService.createClient(request);
    }

    @GetMapping("/api/client/all")
    public GetAllResponse getAllClients(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("elementsOnPage") int elementsOnPage,
            @RequestParam("page") int page
    ) {
        List<Client> clientList = clientService.getAllClients(GetAllRequest.builder()
                .name(name)
                .email(email)
                .elementsOnPage(elementsOnPage)
                .page(page)
                .build());

        List<GetAllResponse.Data> data = clientList.stream()
                .map(client -> GetAllResponse.Data.builder()
                        .id(client.getId().toString())
                        .name(client.getFullName())
                        .email(client.getEmail())
                        .build()
                ).toList();

        return new GetAllResponse(data);
    }

    @GetMapping("/api/client/all_compact")
    public GetAllCompactResponse getAllClients() {
        List<Client> clientList = clientService.getAllClients();

        return new GetAllCompactResponse(clientList.stream().map(User::getFullName).toList());
    }

    @GetMapping("/api/client/all_count")
    public long getTotalClientsCount(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("elementsOnPage") int elementsOnPage
    ) {

        return clientService.getTotalCount(GetTotalClientsCountRequest.builder()
                .name(name)
                .email(email)
                .elementsOnPage(elementsOnPage)
                .build());
    }

    @PostMapping("/api/client/delete_list")
    public void deleteBranches(@RequestBody DeleteClientsRequest request) {
        clientService.deleteClients(request.getIdList().stream().map(UUID::fromString).toList());
    }

}
