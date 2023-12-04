package com.example.springtest.controller;

import com.example.springtest.dto.login.LoginRequest;
import com.example.springtest.dto.login.LoginResponse;
import com.example.springtest.exceptions.controller.NoSuchUserException;
import com.example.springtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = null;

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

        return response;
    }
}
