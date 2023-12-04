package com.example.springtest.dto.clientService;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewClientData {

    private String login;

    private String password;

    private String fullName;

    private String email;

}
