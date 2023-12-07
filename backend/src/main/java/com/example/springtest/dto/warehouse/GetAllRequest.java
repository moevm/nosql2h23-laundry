package com.example.springtest.dto.warehouse;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRequest {

    private String address;

    private String branch;

    private int elementsOnPage;

    private int page;
}

