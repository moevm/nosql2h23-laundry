package com.example.springtest.dto.warehouse;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GetAllResponse {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {

        private String id;

        private String address;

        private String branch;

    }

    private List<Data> data;

}