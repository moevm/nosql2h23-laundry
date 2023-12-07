package com.example.springtest.dto.login;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String id;

    private String name;

    private String role;

}
