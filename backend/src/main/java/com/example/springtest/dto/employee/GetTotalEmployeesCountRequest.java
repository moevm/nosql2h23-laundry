package com.example.springtest.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalEmployeesCountRequest {

    private String name;

    private List<String> roles;

    private String phone;

    private int elementsOnPage;

}
