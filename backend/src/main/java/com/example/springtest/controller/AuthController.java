package com.example.springtest.controller;

import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.login.LoginRequest;
import com.example.springtest.dto.login.LoginResponse;
import com.example.springtest.dto.signUp.SignUpRequest;
import com.example.springtest.dto.signUp.SignUpResponse;
import com.example.springtest.exceptions.controller.NoSuchUserException;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.Client;
import com.example.springtest.model.User;
import com.example.springtest.service.ClientService;
import com.example.springtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final ClientService clientService;

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        User user = userService.getUserByLogin(loginRequest.getLogin()).orElseThrow(NoSuchUserException::new);

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new NoSuchUserException();
        }

        return LoginResponse.builder()
                .id(user.getId().toString())
                .name(user.getFullName())
                .role(user.getRole().toString())
                .build();

        /*LoginResponse response = null;

        switch (loginRequest.getLogin()) {
            case "Client":
                response = LoginResponse.builder()
                        .id("ABF4562375")
                        .name("Kate1")
                        .role("CLIENT")
                        .build();
                break;
            case "Admin":
                response = LoginResponse.builder()
                        .id("ABF453864375")
                        .name("Kate2")
                        .role("ADMIN")
                        .build();
                break;
            case "Director":
                response = LoginResponse.builder()
                        .id("3BFFFFF874")
                        .name("Kate3")
                        .role("DIRECTOR")
                        .build();
                break;
            case "Super":
                response = LoginResponse.builder()
                        .id("6356974")
                        .name("Kate4")
                        .role("SUPERUSER")
                        .build();
                break;
        }

        if (response == null) throw new NoSuchUserException();

        return response;*/
    }

    @PostMapping("/api/sign_up")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {

        Optional<Client> client = clientService.getClientByLogin(request.getLogin());

        if (client.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Client newClient = clientService.createClient(CreateClientRequest.builder()
                        .login(request.getLogin())
                        .password(request.getPassword())
                        .name(request.getFullName())
                        .email(request.getEmail())
                        .build()
        );

        return SignUpResponse.builder()
                .id(newClient.getId().toString())
                .build();
    }
}
