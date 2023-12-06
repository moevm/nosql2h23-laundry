package com.example.springtest.dto.warehouse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteWarehousesRequest {

    private List<String> idList;

}
