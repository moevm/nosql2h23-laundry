package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAllRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Dates {
        private String start;

        private String end;
    }

    private Dates dates;

    private List<String> states;

    private String branch;

    private String clientId;

    private int elementsOnPage;

    private int page;

}
