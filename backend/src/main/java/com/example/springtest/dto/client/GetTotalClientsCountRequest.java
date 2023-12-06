package com.example.springtest.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalClientsCountRequest {

    private String name;

    private String email;

    private int elementsOnPage;

}
