package com.example.springtest.dto.client;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRequest {

    private String name;

    private String email;

    private int elementsOnPage;

    private int page;

}
