package com.example.springtest.dto.branch;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBranchRequest {

    private String address;

    private String directorName;

    private String adminName;

    private String warehouseAddress;

    private List<String> schedule;
}
