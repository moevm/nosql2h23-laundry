package com.example.springtest.controller;

import com.example.springtest.dto.LoginRequest;
import com.example.springtest.dto.LoginResponse;
import com.example.springtest.exceptions.controller.NoSuchUserException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MainController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = null;

        switch (loginRequest.getLogin()) {
            case "Client":
                response = LoginResponse.builder()
                        .id(342568L)
                        .name("Kate1")
                        .role("CLIENT")
                        .build();
                break;
            case "Admin":
                response = LoginResponse.builder()
                        .id(2092456L)
                        .name("Kate2")
                        .role("ADMIN")
                        .build();
                break;
            case "Director":
                response = LoginResponse.builder()
                        .id(432365L)
                        .name("Kate3")
                        .role("DIRECTOR")
                        .build();
                break;
            case "Super":
                response = LoginResponse.builder()
                        .id(182L)
                        .name("Kate4")
                        .role("SUPERUSER")
                        .build();
                break;
        }

        if (response == null) throw new NoSuchUserException();

        return response;
    }
}
