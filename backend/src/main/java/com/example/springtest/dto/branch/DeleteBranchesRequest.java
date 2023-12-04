package com.example.springtest.dto.branch;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBranchesRequest {

    private List<String> idList;

}
