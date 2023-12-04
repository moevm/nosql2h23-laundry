package com.example.springtest.dto.branch;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRequest {

    private String address;

    private String warehouse;

    private String director;

    private int elementsOnPage;

    private int page;
}
