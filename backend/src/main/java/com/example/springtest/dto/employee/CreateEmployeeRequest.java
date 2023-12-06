package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    private String login;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String role;

    private List<String> schedule;

}
