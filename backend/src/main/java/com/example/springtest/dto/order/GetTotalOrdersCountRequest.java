package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalOrdersCountRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Dates {
        private String start;

        private String end;
    }

    private GetAllRequest.Dates dates;

    private List<String> states;

    private String branch;

    private String clientId;

    private int elementsOnPage;

}
