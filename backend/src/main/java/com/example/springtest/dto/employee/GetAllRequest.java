package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRequest {

    private String name;

    private List<String> roles;

    private String phone;

    private int elementsOnPage;

    private int page;

}
