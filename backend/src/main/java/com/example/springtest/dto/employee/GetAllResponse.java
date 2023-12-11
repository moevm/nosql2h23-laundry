package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {

        private String id;

        private String name;

        private String role;

        private String phone;

    }

    private List<Data> data;

}
