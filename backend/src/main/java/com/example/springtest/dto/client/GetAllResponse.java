package com.example.springtest.dto.client;

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

        private String name;

        private String email;

    }

    private List<Data> data;

}
