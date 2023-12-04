package com.example.springtest.controller;

import com.example.springtest.model.Client;
import com.example.springtest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    public @ResponseBody Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @GetMapping("/getAll")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}
