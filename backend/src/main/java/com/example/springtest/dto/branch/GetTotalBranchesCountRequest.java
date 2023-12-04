package com.example.springtest.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalBranchesCountRequest {

    private String address;

    private String warehouse;

    private String director;

    private int elementsOnPage;

}
