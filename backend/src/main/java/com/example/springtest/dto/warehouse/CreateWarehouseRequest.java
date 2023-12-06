package com.example.springtest.dto.warehouse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateWarehouseRequest {

    private String address;

    private String branchAddress;

    private List<String> schedule;

}
