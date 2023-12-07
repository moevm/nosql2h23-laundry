package com.example.springtest.dto.client;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientRequest {

    private String login;

    private String password;

    private String name;

    private String email;

}
